package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.ReviewDTO;

import java.util.Optional;

public interface ReviewService  {

    ResponseObject getAll(Optional<Integer> page);

    ResponseObject getByUsername(String username);

    ResponseObject getByCourse(String id, Optional<Integer> page);

    ResponseObject save(ReviewDTO dto);

    ResponseObject delete(int id);

    ResponseObject update(int id, ReviewDTO dto);
}
