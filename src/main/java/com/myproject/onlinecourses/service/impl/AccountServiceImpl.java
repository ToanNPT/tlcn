package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.aws.AwsS3Service;
import com.myproject.onlinecourses.controller.SecurityController;
import com.myproject.onlinecourses.converter.AccountConvert;
import com.myproject.onlinecourses.dto.*;
import com.myproject.onlinecourses.entity.*;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.exception.NotMatchException;
import com.myproject.onlinecourses.mail.Mail;
import com.myproject.onlinecourses.mail.MailService;
import com.myproject.onlinecourses.repository.*;
import com.myproject.onlinecourses.security.Roles;
import com.myproject.onlinecourses.service.AccountService;
import com.myproject.onlinecourses.utils.Generator;
import com.myproject.onlinecourses.utils.TokenConstants;
import com.myproject.onlinecourses.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.Function;

@Service
@PropertySource("classpath:paginationsize.properties")
public class AccountServiceImpl implements AccountService {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserDetailRepository userDetailRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AccountConvert accountConvert;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    CartRepository cartRepo;


    @Autowired
    TokenRepository tokenRepo;

    @Autowired
    Generator generator;

    @Autowired
    MailService mailService;

    @Autowired
    AwsS3Service awsS3Service;

    @Value("${amazonProperties.avatarUser.bucketName}")
    String bucketname;



    @Override
    public ResponseObject getAllAccount(Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<Account> accountList = accountRepository.findAll(pageable);
        Page<AccountDTO> accountDTOS = accountList.map(new Function<Account, AccountDTO>() {
            @Override
            public AccountDTO apply(Account account) {
                return accountConvert.entityToAccountDTO(account);
            }
        });
        return new ResponseObject(accountDTOS);
    }

    @Override
    public ResponseObject getAccountById(String username){
        Optional<Account> account = accountRepository.findById(username);
        if(!account.isPresent()){
            throw new NotFoundException("Can not found account with username: " + username);
        }
        AccountDTO accountDTO = accountConvert.entityToAccountDTO(account.get());
        return new ResponseObject(accountDTO);
    }

    @Override
    public ResponseObject saveAccount(RegisterAccountForm register, Roles role) throws DuplicateException {
        Optional<UserDetail> check = userDetailRepository.findFirstByPhoneOrEmailOrUsername(register.getPhone(), register.getEmail(),
                register.getUsername());

        if(check.isPresent()){
            throw new DuplicateException("your mail or phone existed");
        }

        Optional<Role> roleModel = roleRepository.findRoleByName(role.value);
        Account account = new Account();
        account.setUsername(register.getUsername());
        account.setPassword(passwordEncoder.encode(register.getPassword()));
        account.setRole(roleModel.get());

        UserDetail userDetail = accountConvert.registerFormToUserDetail(register);
        account.setUserDetail(userDetail);

        if(register.getAvatar() != null){
            String filename  = "avatar" + "-" + register.getUsername();
            String url = awsS3Service.uploadSingleFile(bucketname, filename, register.getAvatar());
            userDetail.setAvatar(url);
        }
        else{
            String url = Utils.randomAvatarUser(register.getGender());
            userDetail.setAvatar(url);
        }

//        if(account == null || userDetail == null || !role.isPresent()){
//            throw new RuntimeException("Some error occurred when creating account");
//        }

        Cart yourCart = new Cart();
        //yourCart.setAccount(savedAccount);
        yourCart.setUsername(register.getUsername());
        yourCart.setTotalPrice(0);
        yourCart.setPaymentPrice(0);
        yourCart.setAccount(account);
        account.setCart(yourCart);

        Account savedAccount = accountRepository.save(account);

        return new ResponseObject(accountConvert.mergerAccountDetail(savedAccount, userDetail));
    }

    @Override
    public ResponseObject deleteAccount(String username){
        Optional<Account> foundAccount = accountRepository.findById(username);
        if(!foundAccount.isPresent()){
            throw new NotFoundException("Can not found username: " + username);
        }
        accountRepository.deleteById(username);
        return new ResponseObject(null);
    }

    @Override
    public ResponseObject updateAvatar(String username, MultipartFile file){
        Optional<Account> account = accountRepository.findById(username);
        if(!account.isPresent())
            throw new NotFoundException("Can not found account");
        String filename = "avatar" + "-" + username + new Date();
        String url = awsS3Service.uploadSingleFile(bucketname, filename, file);
        account.get().getUserDetail().setAvatar(url);
        Account updated = accountRepository.save(account.get());
        return new ResponseObject(updated.getUserDetail().getAvatar());
    }
    @Override
    public ResponseObject updateAccount(String username, UpdateInforForm dto) throws DuplicateException {
        Optional<Account> account = accountRepository.findById(username);
        if(!account.isPresent()){
            throw new NotFoundException("account " + username + " is not found");
        }
        UserDetail userDetail = userDetailRepository.findById(username).get();

        Optional<UserDetail> duplicated = userDetailRepository.findFirstByPhoneOrEmailOrUsername(dto.getPhone(),
                dto.getEmail(), username);
        if(!duplicated.isPresent()){
            throw new DuplicateException("mail, phone or username already existed");
        }

        if(dto.getEmail() != null && dto.getEmail() != userDetail.getEmail())
            userDetail.setEmail(dto.getEmail());

        if(dto.getBirthdate() != null)
            userDetail.setBirthdate(dto.getBirthdate());

        userDetail.setGender(dto.getGender());
        userDetail.setFullname(dto.getFullname());
        userDetail.setPhone(dto.getPhone());

        account.get().setUserDetail(userDetail);
        Account res = accountRepository.save(account.get());
        return new ResponseObject(accountConvert.accountToDetail(res));
    }

    @Override
    public ResponseObject getAccountDetail(String username){
        Optional<Account> account = accountRepository.findById(username);
        if(!account.isPresent()) throw new NotFoundException("Can not find account by username:" + username );
        return new ResponseObject(accountConvert.accountToDetail(account.get()));
    }

    @Override
    public ResponseObject changePassword(ChangePassword dto){
        Optional<Account> account = accountRepository.findById(dto.getUsername());
        if(!account.isPresent())
            throw new NotFoundException("Can not find your account: " + dto.getUsername());

        if(!dto.getConfirmPwd().equals(dto.getNewPwd()))
            throw new NotMatchException("Confirm password does not match");

        boolean check = passwordEncoder.matches(dto.getOldPwd(), account.get().getPassword());
        if(!check){
            throw new NotMatchException("Your old password does not match");
        }
        if(dto.getOldPwd().equals(dto.getNewPwd()))
            throw new DuplicateException("Your old pass matches with new pass, please try again");

        String pwdHashcode = passwordEncoder.encode(dto.getNewPwd());
        account.get().setPassword(pwdHashcode);
        accountRepository.save(account.get());
        return new ResponseObject("", "200", "Update new password successful", true);
    }

    @Override
    public ResponseObject sendResetPwdLink(String email){
        Optional<Account> account = accountRepository.findAccountByEmail(email);
        if(!account.isPresent())
            throw new NotFoundException("Can not find your account with mail " + email);

        List<Token> validTokens = tokenRepo.getValidTokenByUsername(account.get().getUsername(), new Date());
        if(!validTokens.isEmpty())
            throw new DuplicateException("You jus request reset password");

        Date iat = new Date();
        String valueToken = generator.generateToken();
        Token token = new Token();
        token.setAccount(account.get());
        token.setIat(iat);
        token.setExp(new Date(iat.getTime() + TokenConstants.PWD_RESET_TIME_TOKEN));
        token.setToken(valueToken);

        tokenRepo.save(token);
        Mail prepareMail = mailService.createTokenMail(account.get(), "REQUEST RESET PASSWORD", valueToken);
        mailService.sendMail(prepareMail, "reset-password-mail");
        return new ResponseObject("", "200", "Send reset mail successfully", null);
    }

    @Override
    public ResponseObject resetPassword(String token, ResetPass dto){
        Optional<Token> found = tokenRepo.getTokenByTokenValue(token);
        if(!found.isPresent())
            throw new NotFoundException("Token is not found");

        Date date = new Date();
        if(date.after(found.get().getExp()) || date.before(found.get().getIat())){
            throw new RuntimeException("Token is not valid");
        }

        if(!dto.getNewPwd().equals(dto.getConfirmPwd())){
            throw new NotMatchException("Your confirm password not matches");
        }

        Optional<Account> account = accountRepository.findById(found.get().getAccount().getUsername());
        String hashPwd = passwordEncoder.encode(dto.getNewPwd());
        account.get().setPassword(hashPwd);
        accountRepository.save(account.get());
        tokenRepo.delete(found.get());
        return new ResponseObject("", "200", "Reset password successful", null);
    }
}
