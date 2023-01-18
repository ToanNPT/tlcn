package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.UserDetail;
import com.myproject.onlinecourses.exception.ForbiddenException;
import com.myproject.onlinecourses.security.CustomUserDetails;
import com.myproject.onlinecourses.security.Roles;
import com.myproject.onlinecourses.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class OrderController {
    @Autowired
    OrderService orderService;

    @GetMapping("orders")
    public ResponseObject getOrdersActive(@RequestParam("page")Optional<Integer> page,
                                          @RequestParam("limit")Optional<Integer> limit){
        return orderService.getAllActiveOrder(page, limit);
    }

    @GetMapping("orders/{orderId}")
    public ResponseObject getOrderById(@PathVariable("orderId") String id,Principal principal){
        if(principal == null)
            throw new ForbiddenException();
        return orderService.getDetailOrderById(id,principal.getName());

    }

}
