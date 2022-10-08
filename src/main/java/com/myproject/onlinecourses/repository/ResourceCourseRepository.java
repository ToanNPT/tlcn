package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.ResourcesCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ResourceCourseRepository extends JpaRepository<ResourcesCourse, Integer> {
    List<ResourcesCourse> getAllByCourse_Id(String id);
}
