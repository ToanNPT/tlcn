package com.myproject.onlinecourses.security;

import com.myproject.onlinecourses.filters.*;
import com.myproject.onlinecourses.repository.AccountRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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

    @Bean
    public RestAccessDeniedHandler accessDeniedHandler(){return new RestAccessDeniedHandler();}

    @Bean
    public RestAuthenticationEntryPoint authenticationEntryPoint(){return new RestAuthenticationEntryPoint();}
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }


//    @Bean
//    CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
//        configuration.setAllowedMethods(Arrays.asList("GET","POST", "PUT", "DELETE"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/**", configuration);
//        return source;
//    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors().configurationSource(request-> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
            configuration.setAllowedMethods(Arrays.asList("GET","POST", "DELETE", "PUT"));
            configuration.setAllowedHeaders(List.of("*"));
            return configuration;
        });

        http.authorizeHttpRequests()
                .antMatchers(HttpMethod.POST,"/api/v1/login").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator").permitAll()
                .antMatchers(HttpMethod.GET, "/actuator/*").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/courses/search").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/accounts").hasAuthority(Roles.ADMIN.value)
                .antMatchers(HttpMethod.GET, "/api/v1/account").hasAnyAuthority(Roles.ADMIN.value, Roles.USER.value)
                .antMatchers(HttpMethod.POST, "/api/v1/account").hasAuthority(Roles.ADMIN.value)
                .antMatchers(HttpMethod.GET, "/ai/v1/logout").hasAnyAuthority(Roles.ADMIN.value, Roles.USER.value)
                .antMatchers(HttpMethod.POST, "/api/v1/account/users/register").permitAll()
                .anyRequest().permitAll();


        http.addFilter(new JwtAuthenticationFilter(authenticationManagerBean(), new JwtProvider(),accountRepo ));
        http.addFilter(new JWTAuthorizationFilter(authenticationManagerBean(), userDetailsService));
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(exceptionHandlerFilter(), JwtAuthenticationFilter.class);
        //http.addFilterBefore(forbiddenException(), JWTAuthorizationFilter.class);
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler()).authenticationEntryPoint(authenticationEntryPoint());
    }

}
