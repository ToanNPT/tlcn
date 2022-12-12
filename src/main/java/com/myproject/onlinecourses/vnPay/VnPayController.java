package com.myproject.onlinecourses.vnPay;

import com.google.gson.Gson;
import com.myproject.onlinecourses.dto.RequestOrder;
import com.myproject.onlinecourses.dto.ResponseObject;

import com.myproject.onlinecourses.repository.CartRepository;
import com.myproject.onlinecourses.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/")
public class VnPayController {

    @Autowired
    VnPayService vnPayService;

    @Autowired
    OrderService orderService;

    @Autowired
    CartRepository cartRepository;

    @PostMapping("vnpay/request-pay")
    public ResponseEntity<?> requestPayment(@Validated @RequestBody RequestOrder orderForm,
                                         BindingResult bindingResult,
                                         HttpServletRequest request,
                                         HttpServletResponse res) throws UnsupportedEncodingException {

        if(bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());

        boolean isValid = orderService.checkRequestOrder(orderForm);
        if(!isValid)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject("500", "200", "something error", null));

        String orderId = Config.getRandomNumber(8);
        orderService.addUnActiveOrder(orderId, orderForm);
        String url = vnPayService.getUrlPay(request, orderForm, orderId);

        return ResponseEntity.status(HttpStatus.OK).location(URI.create(url)).build();
    }

    //coming soon
    @GetMapping("vnpay/payment/result")
    public ResponseEntity<?> ipnCalled(){
        System.out.println("IPN VNPAY CALLED");
        Gson gson = new Gson();
        String json = gson.toJson("{RspCode: '00', Message: 'success'}");
        return ResponseEntity.status(200).body(json);
    }


}
