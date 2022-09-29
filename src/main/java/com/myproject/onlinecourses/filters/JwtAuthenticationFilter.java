package com.myproject.onlinecourses.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.security.CustomUserDetails;
import com.myproject.onlinecourses.security.JwtProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.*;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


    private AccountRepository accountRepo;

    private JwtProvider jwtProvider;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, AccountRepository accountRepo){
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.accountRepo = accountRepo;
        setFilterProcessesUrl("/api/v1/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Account cred = new ObjectMapper().readValue(request.getInputStream(), Account.class);
            Optional<Account> found = accountRepo.findById(cred.getUsername());
            if(!found.isPresent())
                throw new RuntimeException("Can not found username");
            return authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(
                            cred.getUsername(),
                            cred.getPassword(),
                            Collections.singletonList(new SimpleGrantedAuthority(found.get().getRole().getName()))));

        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        String accessToken = jwtProvider.generateAccessToken((CustomUserDetails) authResult.getPrincipal());
        String body = accessToken;

        Map<String, String> jwtRes = new HashMap<>();
        jwtRes.put("token", accessToken);
        ResponseObject res = new ResponseObject(jwtRes);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), res);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        ResponseObject customResponse = new ResponseObject("400", "200", "Username or password is wrong!", null);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        new ObjectMapper().writeValue(response.getOutputStream(), customResponse);
    }


}