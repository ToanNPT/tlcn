package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.ReviewConverter;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.ReviewDTO;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.entity.Review;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.repository.ReviewRepository;
import com.myproject.onlinecourses.service.AccountService;
import com.myproject.onlinecourses.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    ReviewRepository reviewRepo;

    @Autowired
    ReviewConverter converter;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CoursesRepository coursesRepo;

    @Override
    public ResponseObject getById(Integer id){
        Optional<Review> review = reviewRepo.findById(id);
        if(!review.isPresent()){
            throw new NotFoundException("Can not found review id "+ id);
        }
        return new ResponseObject(converter.entityToDto(review.get()));
    }

    @Override
    public ResponseObject getAll(Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.orElse(0), 10);
        Page<Review> reviews =reviewRepo.findAll(pageable);
        Page<ReviewDTO> dtos = reviews.map(new Function<Review, ReviewDTO>() {
            @Override
            public ReviewDTO apply(Review review) {
                return converter.entityToDto(review);
            }
        });

        return new ResponseObject(dtos);
    }

    @Override
    public ResponseObject getByUsername(String username){
        Optional<Account> account = accountRepo.findById(username);
        if(!account.isPresent())
            throw new NotFoundException("Can not find username: " + username);
        List<Review> reviews = reviewRepo.findByAccount_Username(username);
        List<ReviewDTO> reviewDTOS = reviews.stream()
                .map(r -> converter.entityToDto(r)).collect(Collectors.toList());
        return new ResponseObject(reviewDTOS);
    }

    @Override
    public ResponseObject getByCourse(String id, Optional<Integer> page){
        Optional<Course> course = coursesRepo.findById(id);
        if(!course.isPresent())
            throw new NotFoundException("course " + id + " is not found");
        Pageable pageable = PageRequest.of(page.orElse(0), 10 );
        Page<Review> reviews = reviewRepo.findByCourse_Id(id, pageable );
        Page<ReviewDTO> reviewDTOS = reviews.map(new Function<Review, ReviewDTO>() {
            @Override
            public ReviewDTO apply(Review review) {
                return converter.entityToDto(review);
            }
        });
        return new ResponseObject(reviewDTOS);
    }

    @Override
    public ResponseObject save(ReviewDTO dto){
        Review newReview = converter.dtoToEntity(dto);
        Optional<Account> account = accountRepo.findById(dto.getUsername());
        Optional<Course> course = coursesRepo.findById(dto.getCourseId());
        if(!course.isPresent()) throw new NotFoundException("Course is not found");
        if(!account.isPresent()) throw new NotFoundException("Account " + dto.getUsername() +" is not found");
        newReview.setAccount(account.get());
        newReview.setCourse(course.get());
        newReview.setCreateDate(new Date());
        Review res = reviewRepo.save(newReview);
        return new ResponseObject(converter.entityToDto(res));
    }

    @Override
    public ResponseObject delete(int id){
        Optional<Review> review = reviewRepo.findById(id);
        if(!review.isPresent()) throw new NotFoundException("Can not found this review");
        reviewRepo.delete(review.get());
        return new ResponseObject("", "200", "delete successful", null);
    }

    @Override
    public ResponseObject update(int id, ReviewDTO dto){
        Optional<Review> found = reviewRepo.findById(id);
        if(!found.isPresent()) throw new NotFoundException("Can not found this review");
        found.get().setContent(dto.getContent());
        found.get().setUpdateDate(new Date());
        found.get().setRate(dto.getRate());
        Review res = reviewRepo.save(found.get());
        return new ResponseObject(converter.entityToDto(res));
    }
}
