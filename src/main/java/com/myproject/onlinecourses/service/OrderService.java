package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.OrderDTO;
import com.myproject.onlinecourses.dto.RequestOrder;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Order;

import java.util.Optional;

public interface OrderService {
    OrderDTO addOrder(RequestOrder dto);

    Order addUnActiveOrder(String paymentId, RequestOrder dto);

    boolean checkRequestOrder(RequestOrder dto);

    void activeOrder(String orderId);

    void deleteUnActiveorder(String orderId);

    double calcPaymentPrice(RequestOrder dto);

    boolean checkInforFromVnpay(String vnp_txtRef, String vnp_amount);

    void handleActiveOrder(String orderId);

    ResponseObject getAllActiveOrder(Optional<Integer> page, Optional<Integer> limit);

    ResponseObject getDetailOrderById(String orderId, String username);
}
