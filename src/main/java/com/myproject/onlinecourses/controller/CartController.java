package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class CartController {
    @Autowired
    CartService cartService;

    @GetMapping("cart/{username}")
    public ResponseObject getByUsername(@PathVariable("username") String username){
        return cartService.getByUsername(username);
    }

    @DeleteMapping("cart/{username}/{id}")
    public ResponseObject delete(@PathVariable("id") String courseId, @PathVariable("username") String username){
        return cartService.delete(courseId, username);
    }

    @PostMapping("cart/{username}/{courseId}")
    public ResponseObject save(@PathVariable("username") String username,
                               @PathVariable("courseId") String courseId){
        return cartService.add(username, courseId);
    }
}
