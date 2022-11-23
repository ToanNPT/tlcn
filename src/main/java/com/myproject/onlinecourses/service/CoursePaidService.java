package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Course;
import org.springframework.data.domain.Page;

public interface CoursePaidService {


    ResponseObject insertCoursePaidFromSuccessPayment(String orderId);
}
