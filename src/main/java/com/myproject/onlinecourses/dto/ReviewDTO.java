package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Course;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDTO {
    private int id;
    private String username;
    private String courseId;
    private String content;
    private String rate;
    private Date createDate;
    private Date updateDate;
}
