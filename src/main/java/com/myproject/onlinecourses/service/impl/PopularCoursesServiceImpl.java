package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.CourseConverter;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.service.PopularCoursesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PopularCoursesServiceImpl implements PopularCoursesService {

    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    CourseConverter converter;
    @Override
    public ResponseObject getNewestCourse(int limit){
        List<Course> courseList = coursesRepo.findNewestCourses(limit);
        return new ResponseObject(courseList.stream()
                .map(c -> converter.entityToCourseDTO(c))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseObject getTopNumStudents(int limit){
        List<Course> courseList = coursesRepo.findByTopNumStudents(limit);
        return new ResponseObject(courseList.stream()
                .map(c -> converter.entityToCourseDTO(c))
                .collect(Collectors.toList()));
    }
}
