package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.CategoryDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

public interface CategoryService {
    ResponseObject getAll();

    ResponseObject getById(String id);

    ResponseObject update(String id, CategoryDTO categoryDTO);

    ResponseObject delete(String id);

    ResponseObject saveNewCategory(CategoryDTO dto);
}
