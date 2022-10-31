package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.controller.SecurityController;
import com.myproject.onlinecourses.converter.AccountConvert;
import com.myproject.onlinecourses.dto.AccountDTO;
import com.myproject.onlinecourses.dto.AccountDetailDTO;
import com.myproject.onlinecourses.dto.ChangePassword;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Cart;
import com.myproject.onlinecourses.entity.Role;
import com.myproject.onlinecourses.entity.UserDetail;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.exception.NotMatchException;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.CartRepository;
import com.myproject.onlinecourses.repository.RoleRepository;
import com.myproject.onlinecourses.repository.UserDetailRepository;
import com.myproject.onlinecourses.service.AccountService;
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

import java.util.Date;
import java.util.Optional;
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

    @Value("${account.get-all.size}")
    int getAllSize;

    @Override
    public ResponseObject getAllAccount(Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.orElse(0), getAllSize);
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
    public ResponseObject saveAccount(AccountDetailDTO accountDetailDTO) throws DuplicateException {
        Optional<UserDetail> check = userDetailRepository.findFirstByPhoneOrEmailOrUsername(accountDetailDTO.getPhone(), accountDetailDTO.getEmail(),
                accountDetailDTO.getUsername());

        if(check.isPresent()){
            throw new DuplicateException("your mail or phone existed");
        }

        Account account = accountConvert.accountDetailToAccount(accountDetailDTO);
        UserDetail userDetail = accountConvert.accountDetailToUserDetail(accountDetailDTO);
        Optional<Role> role = roleRepository.findById(accountDetailDTO.getRole());

        if(account == null || userDetail == null || !role.isPresent()){
            throw new RuntimeException("Some error occurred when creating account");
        }

        String hashPwd = passwordEncoder.encode(accountDetailDTO.getPassword());

        account.setRole(role.get());
        account.setPassword(hashPwd);
        userDetail.setUsername(account.getUsername());
        account.setUserDetail(userDetail);

        Cart yourCart = new Cart();
        //yourCart.setAccount(savedAccount);
        yourCart.setUsername(accountDetailDTO.getUsername());
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
    public ResponseObject updateAccount(String username, AccountDetailDTO dto) throws DuplicateException {
        Optional<Account> account = accountRepository.findById(dto.getUsername());
        if(!account.isPresent()){
            throw new NotFoundException("account " + username + " is not found");
        }
        UserDetail userDetail = userDetailRepository.findById(username).get();

        Optional<UserDetail> duplicated = userDetailRepository.findFirstByPhoneOrEmailOrUsername(dto.getPhone(),
                dto.getEmail(), dto.getUsername());
        if(!duplicated.isPresent()){
            throw new DuplicateException("mail, phone or username already existed");
        }

        userDetail.setEmail(dto.getEmail());
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

}
