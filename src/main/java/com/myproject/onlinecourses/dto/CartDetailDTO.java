package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Cart;
import com.myproject.onlinecourses.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CartDetailDTO {
    private int id;
    private String courseId;
}
