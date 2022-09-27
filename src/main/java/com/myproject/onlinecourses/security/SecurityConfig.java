package com.myproject.onlinecourses.security;

import com.myproject.onlinecourses.filters.ExceptionHandlerFilter;
import com.myproject.onlinecourses.filters.JWTAuthorizationFilter;
import com.myproject.onlinecourses.filters.JwtAuthenticationFilter;
import com.myproject.onlinecourses.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();
    }

    @Bean
    public ExceptionHandlerFilter exceptionHandlerFilter(){return new ExceptionHandlerFilter();};

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/login").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/accounts").hasAuthority(Roles.ADMIN.value)
                .antMatchers(HttpMethod.GET, "/api/v1/account").hasAnyAuthority(Roles.ADMIN.value, Roles.USER.value)
                .antMatchers(HttpMethod.POST, "/api/v1/account").hasAuthority(Roles.ADMIN.value)
                .antMatchers(HttpMethod.GET, "/ai/v1/logout").hasAnyAuthority(Roles.ADMIN.value, Roles.USER.value)
                .anyRequest().authenticated();

        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), new JwtProvider(),accountRepo ));
        http.addFilter(new JWTAuthorizationFilter(authenticationManagerBean(), userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(exceptionHandlerFilter(), JwtAuthenticationFilter.class);
        http.addFilterBefore(exceptionHandlerFilter(), JWTAuthorizationFilter.class);
    }


}
