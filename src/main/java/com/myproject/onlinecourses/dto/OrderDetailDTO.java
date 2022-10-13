package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailDTO {
    private int id;
    private String courseId;
    private double price;
}
