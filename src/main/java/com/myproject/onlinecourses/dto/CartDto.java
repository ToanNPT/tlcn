package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {
    private String username;
    private double totalPrice;
    private double paymentPrice;
    private List<CartDetailDTO> cartDetailList;
}
