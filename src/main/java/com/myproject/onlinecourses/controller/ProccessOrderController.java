package com.myproject.onlinecourses.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

public class ProccessOrderController {

    @PostMapping("paying/payments/{paymentID}")
    public void requestOrder(@PathVariable("paymentID") String paymentID){

    }
}
