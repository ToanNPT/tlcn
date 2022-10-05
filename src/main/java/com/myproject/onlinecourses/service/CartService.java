package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResponseObject;

public interface CartService {
    ResponseObject getByUsername(String username);

    ResponseObject delete(int id, String username);

    ResponseObject add(String username, String courseId);
}
