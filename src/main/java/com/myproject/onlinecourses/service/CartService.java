package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResponseObject;

import java.util.List;

public interface CartService {
    ResponseObject getCartDetailById(Integer id, String username);

    ResponseObject getByUsername(String username);

    ResponseObject delete(String id, String username);

    ResponseObject add(String username, String courseId);

    void deleteFromOrder(String username, List<String> idCourses);
}
