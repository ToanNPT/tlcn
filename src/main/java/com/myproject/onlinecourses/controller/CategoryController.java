package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.CategoryDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.CategoryService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class CategoryController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("categories")
    public ResponseObject getAll(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("limit") Optional<Integer> limit){
        return categoryService.getAll(page, limit);
    }

    @GetMapping("category/{id}")
    public ResponseObject getById(@PathVariable("id") String id){
        return categoryService.getById(id);
    }

    @PutMapping("category/update/{id}")
    public ResponseObject update(@PathVariable("id") String id, @RequestBody CategoryDTO dto){
        return categoryService.update(id, dto);
    }

    @DeleteMapping("category/delete/{id}")
    public ResponseObject delete(@PathVariable("id") String id){
        return categoryService.delete(id);
    }

    @PostMapping("category/add")
    public ResponseObject save(@RequestBody CategoryDTO dto){
        return categoryService.saveNewCategory(dto);
    }
}
