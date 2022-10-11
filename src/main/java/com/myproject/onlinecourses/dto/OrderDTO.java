package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Coupon;
import com.myproject.onlinecourses.entity.OrderDetail;
import com.myproject.onlinecourses.entity.Payment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDTO {
    private String id;
    private String username;
    private double totalPrice;
    private double paymentPrice;
    private String couponCode;
    private Date createDate;
    private int qty;
    private boolean isActive;
    private String paymentName;
    List<OrderDetail> orderDetailList;
}
