package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrder {
    private String username;
    private String couponCode;
    private String paymentId;
    List<String> orderDetailList;
}
