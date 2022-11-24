package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.*;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.security.Roles;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

public interface AccountService {
    ResponseObject getAllAccount(Optional<Integer> page);

    ResponseObject getAccountById(String username);

    ResponseObject saveAccount(RegisterAccountForm register, Roles role) throws DuplicateException;

    ResponseObject deleteAccount(String username);

    ResponseObject updateAvatar(String username, MultipartFile file);

    ResponseObject updateAccount(String username, UpdateInforForm dto) throws DuplicateException;

    ResponseObject getAccountDetail(String username);

    ResponseObject changePassword(ChangePassword dto);

    ResponseObject sendResetPwdLink(String email);

    ResponseObject resetPassword(String token, ResetPass dto);
}
