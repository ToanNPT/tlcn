package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.CategoryDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

import java.util.Optional;

public interface CategoryService {
    ResponseObject getAll(Optional<Integer> page, Optional<Integer> limit);

    ResponseObject getById(String id);

    ResponseObject update(String id, CategoryDTO categoryDTO);

    ResponseObject delete(String id);

    ResponseObject saveNewCategory(CategoryDTO dto);
}
