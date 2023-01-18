package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.exception.ForbiddenException;
import com.myproject.onlinecourses.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("cart")
    public ResponseObject getByUsername(Principal principal){
        if(principal == null)
            throw new ForbiddenException();
        return cartService.getByUsername(principal.getName());
    }

    @DeleteMapping("cart/{id}")
    public ResponseObject delete(@PathVariable("id") String courseId, Principal principal){
        if( principal == null)
            throw new ForbiddenException();
        return cartService.delete(courseId, principal.getName());
    }

    @PostMapping("cart/{courseId}")
    public ResponseObject save(Principal principal, @PathVariable("courseId") String courseId){
        if(principal == null)
            throw new ForbiddenException();

        return cartService.add(principal.getName(), courseId);
    }

    @GetMapping("cart-detail/{id}")
    public ResponseObject getCartDetailById(@PathVariable("id") Integer id, Principal principal){
        if(principal == null)
            throw new ForbiddenException();
        return cartService.getCartDetailById(id, principal.getName());
    }

}
