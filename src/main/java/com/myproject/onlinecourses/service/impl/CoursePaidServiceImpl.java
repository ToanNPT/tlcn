package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.CourseConverter;
import com.myproject.onlinecourses.dto.CourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.*;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.CoursePaidRepository;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.repository.OrderRepository;
import com.myproject.onlinecourses.service.CoursePaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Service
public class CoursePaidServiceImpl implements CoursePaidService {
    @Autowired
    CoursePaidRepository coursePaidRepo;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    CourseConverter converter;

    @Override
    public ResponseObject insertCoursePaidFromSuccessPayment(String orderId){
        Optional<Order> order = orderRepository.findById(orderId);
        if(!order.isPresent())
            throw new NotFoundException("Can not found order ");
        for(OrderDetail o: order.get().getOrderDetailList()){
            CoursePaid paid = new CoursePaid();
            paid.setCourse(o.getCourse());
            paid.setAccount(o.getAccount());
            paid.setBuyDate(o.getOrder().getCreateDate());
            coursePaidRepo.save(paid);
        }
        return new ResponseObject("", "200", "success", null);
    }

    public ResponseObject getListCoursePaidByUsername(String username, Optional<Integer> page,
                                                      Optional<Integer> limit){
        Pageable pageable = PageRequest.of(page.orElse(0), limit.orElse(20), Sort.by("buyDate").descending());
        Page<Course> list = coursePaidRepo.getListCoursePaidByUsername(username, pageable);
        Page<CourseDTO> dtoList = list.map(new Function<Course, CourseDTO>() {
            @Override
            public CourseDTO apply(Course course) {
                return converter.entityToCourseDTO(course);
            }
        });
        return new ResponseObject(dtoList);
    }

    public ResponseObject checkIsCoursePaid(String username, String courseID){
        return new ResponseObject(null);
    }
}
