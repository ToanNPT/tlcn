package com.myproject.onlinecourses.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.onlinecourses.dto.JwtResponse;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.security.CustomUserDetails;
import com.myproject.onlinecourses.security.JwtProvider;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;


    private AccountRepository accountRepo;

    private JwtProvider jwtProvider;
    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtProvider jwtProvider, AccountRepository accountRepo){
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.accountRepo = accountRepo;
        setFilterProcessesUrl("/api/v1/login/**");

    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Account cred = new ObjectMapper().readValue(request.getInputStream(), Account.class);
            Optional<Account> found = accountRepo.findById(cred.getUsername());
            if(!found.isPresent())
                throw new RuntimeException("Can not found username");

            String pathInfo = request.getRequestURI();
            log.info("PATH LOGIN: " + pathInfo);
            if(pathInfo != null && pathInfo.equals("/api/v1/login/admin")){
                if(!found.get().getRole().getName().equals("ADMIN") || !found.get().getRole().getName().equals("TEACHER")){
                    throw new RuntimeException("You do not have permission to access this page");
                }
            }

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
        JwtResponse jwtResponse = new JwtResponse();
        jwtResponse.setUsername(authResult.getName());
        jwtResponse.setToken(accessToken);
        ResponseObject res = new ResponseObject(jwtResponse);
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
