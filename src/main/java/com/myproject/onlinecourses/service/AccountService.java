package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.AccountDetailDTO;
import com.myproject.onlinecourses.dto.ChangePassword;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.exception.DuplicateException;

import java.util.Optional;

public interface AccountService {
    ResponseObject getAllAccount(Optional<Integer> page);

    ResponseObject getAccountById(String username);

    ResponseObject saveAccount(AccountDetailDTO accountDetailDTO) throws DuplicateException;

    ResponseObject deleteAccount(String username);

    ResponseObject updateAccount(String username, AccountDetailDTO dto) throws DuplicateException;

    ResponseObject getAccountDetail(String username);

    ResponseObject changePassword(ChangePassword dto);
}
