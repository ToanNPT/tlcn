package com.myproject.onlinecourses.filters;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.myproject.onlinecourses.entity.UserDetail;
import com.myproject.onlinecourses.exception.ForbiddenException;
import com.myproject.onlinecourses.security.CustomUserDetails;
import com.myproject.onlinecourses.security.CustomUserDetailsService;
import com.myproject.onlinecourses.security.JwtProvider;
import com.myproject.onlinecourses.utils.SecurityConstants;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    CustomUserDetailsService userDetailsService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomUserDetailsService customUserDetailsService) {
        super(authenticationManager);
        this.userDetailsService = customUserDetailsService;
    }

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, AuthenticationEntryPoint authenticationEntryPoint) {
        super(authenticationManager, authenticationEntryPoint);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.HEADER_STRING);

        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        try{
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        }catch(ForbiddenException e){
            throw new RuntimeException(e.getMessage());
        }
    }



    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request)
    throws ForbiddenException{
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        token = token.replace(SecurityConstants.TOKEN_PREFIX, "");
        if(token != null ){
            String user = Jwts.parser()
                    .setSigningKey(SecurityConstants.SECRET)
                    .parseClaimsJws(token)
                    .getBody().getSubject();

            if (user != null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(user);
                return new UsernamePasswordAuthenticationToken(user, null,userDetails.getAuthorities());
            }
            return null;
        }
        else{
                throw new RuntimeException("Access token did not attached");
        }
    }
}
