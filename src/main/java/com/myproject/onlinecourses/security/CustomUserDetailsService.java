package com.myproject.onlinecourses.security;

import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findById(username).get();
        if(account == null)
            throw new UsernameNotFoundException("Can not find username: " + username);
        return new CustomUserDetails(account);
    }
}
