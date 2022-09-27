package com.myproject.onlinecourses.filters;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.exception.ForbiddenException;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ExceptionHandlerFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request, response);
        }
        catch(RuntimeException e){
            ResponseObject res = new ResponseObject("400", "200", e.getMessage(), null);
            response.getWriter().write(convertToJson(res));
        }

    }

    public String convertToJson(Object object) throws JsonProcessingException {
        if(object == null)
            return null;
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }
}
