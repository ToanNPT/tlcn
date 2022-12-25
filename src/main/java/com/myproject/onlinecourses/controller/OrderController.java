package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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
    public ResponseObject getOrderById(@PathVariable("orderId") String id){
        return orderService.getDetailOrderById(id);
    }

}
