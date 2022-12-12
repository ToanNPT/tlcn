package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestOrder {
    private String username;
    private String couponCode;
    private String paymentId;

    @NotEmpty(message = "No course present in request order")
    List<String> orderDetailList;
}
