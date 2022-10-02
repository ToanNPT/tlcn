package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Category;

import java.util.Date;

public class NewCourse {
    private String id;
    private String categoryId;
    private String language;
    private String name;
    private String description;
    private String accountName;
    private String price;
    private boolean isActive;
    private Date createDate;
    private Date updateDate;
    private int numStudents;
    private boolean isPublic;
}
