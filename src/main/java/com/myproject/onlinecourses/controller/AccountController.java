package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.*;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.security.Roles;
import com.myproject.onlinecourses.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/api/v1/")
public class AccountController {
    @Autowired
    AccountService accountService;

    @GetMapping("accounts")
    @ResponseBody
    public ResponseObject getAllAccount(@RequestParam("page") Optional<Integer> page){
        return accountService.getAllAccount(page);
    }

    @GetMapping("account/{username}")
    public ResponseObject getAccountById(@PathVariable("username") String username ){
        return accountService.getAccountById(username);
    }

//    @PostMapping("account")
//    public ResponseObject saveAccount(@Validated @RequestBody AccountDetailDTO dto, BindingResult bindingResult) throws DuplicateException {
//        if(bindingResult.hasErrors())
//            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
//        return accountService.saveAccount(dto);
//    }

    @DeleteMapping("account/{username}")
    public ResponseObject deleteAccount(@PathVariable("username") String username){
        return accountService.deleteAccount(username);
    }

    @PutMapping("account/{username}")
    public ResponseObject updateAccount(@PathVariable("username") String username,
                                        @Validated @RequestBody UpdateInforForm form,
                                        BindingResult bindingResult ) throws DuplicateException {
        if(bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return accountService.updateAccount(username, form);
    }

    @GetMapping("account/detail/{username}")
    public ResponseObject getAccountDetail(@PathVariable("username") String username){
        return accountService.getAccountDetail(username);
    }

    @PostMapping("account/changePassword")
    public ResponseObject changePassword( @Validated @RequestBody ChangePassword dto,
                                          BindingResult bindingResult,
                                          HttpServletRequest req,
                                          HttpServletResponse res){
        if(bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
            //return new ResponseObject("400", "200", bindingResult.getFieldError().getDefaultMessage(), null);
        }

        ResponseObject result = accountService.changePassword(dto);
        if(result.getErrorCode() == "" || result.getErrorCode() == null){
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if(auth != null){
                new SecurityContextLogoutHandler().logout(req, res, auth);
                return result;
            }
        }
        return new ResponseObject("400", "200", "Can not update password, something was wrong", null);
    }

    @PostMapping("account/requestResetPassword/{email}")
    public ResponseObject sendResetMail(@PathVariable String email){
        return accountService.sendResetPwdLink(email);
    }

    @PostMapping("account/resetPassword/{token}")
    public ResponseObject resetPassword(@PathVariable("token") Optional<String> token, @RequestBody ResetPass dto){
        if(!token.isPresent())
            throw new NotFoundException("Token is not found");
        return accountService.resetPassword(token.get(), dto);

    }

    @PostMapping(value = "account/users/register", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseObject insertUserAccount(@Validated @ModelAttribute RegisterAccountForm dto, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        }
        return accountService.saveAccount(dto, Roles.USER);
    }

    @PostMapping(value = "account/{username}/avatar", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseObject updateAvatar(@PathVariable("username") String username,
                                       @RequestParam("file") MultipartFile file){
        return accountService.updateAvatar(username, file);
    }


}
