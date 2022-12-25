package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.OrderDetail;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetailOrder {
    private String id;
    private String username;
    private double totalPrice;
    private double paymentPrice;
    private String couponCode;
    private Date createDate;
    private int qty;
    private boolean isActive;
    private String paymentName;
    private List<CourseDTO> orderDetailList;
}
