package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.ReviewDTO;
import com.myproject.onlinecourses.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class ReviewController {
    @Autowired
    ReviewService reviewService;

    @GetMapping("reviews/{id}")
    public ResponseObject getById(@PathVariable("id") Integer id){
        return reviewService.getById(id);
    }

    @GetMapping("reviews")
    public ResponseObject getAll(@RequestParam("page")Optional<Integer> page){
        return reviewService.getAll(page);
    }

    @GetMapping("reviews/course/{id}")
    public ResponseObject getByCourseId(@PathVariable("id") String id, @RequestParam("page")Optional<Integer> page){
        return reviewService.getByCourse(id, page);
    }

    @GetMapping("reviews/user/{id}")
    public ResponseObject getByUser(@PathVariable("id") String id, @RequestParam("page")Optional<Integer> page){
        return reviewService.getByUsername(id);
    }

    @PostMapping("review/add")
    public ResponseObject save(@RequestBody ReviewDTO dto){
        return reviewService.save(dto);
    }

    @DeleteMapping("review/delete/{id}")
    public ResponseObject delete(@PathVariable("id") Integer id){
        return reviewService.delete(id);
    }

    @PutMapping("review/update/{id}")
    public ResponseObject update(@PathVariable("id") Integer id, @Validated @RequestBody ReviewDTO dto,
                                 BindingResult bindingResult){
        if(bindingResult.hasErrors())
            throw new RuntimeException(bindingResult.getFieldError().getDefaultMessage());
        return reviewService.update(id, dto);
    }

}
