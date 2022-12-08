package com.myproject.onlinecourses.processPayment;

import com.myproject.onlinecourses.paypal.PayPalService;
import com.myproject.onlinecourses.repository.CouponRepository;
import com.myproject.onlinecourses.repository.OrderRepository;
import com.myproject.onlinecourses.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InternalOrderService {
    @Autowired
    OrderRepository orderRepository;

    @Autowired
    private PayPalService paypalService;

    @Autowired
    private OrderService orderService;

    @Autowired
    CouponRepository couponRepo;

    public void executeOrderToDb(){

    }

}
