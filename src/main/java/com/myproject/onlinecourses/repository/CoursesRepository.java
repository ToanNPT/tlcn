package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CoursesRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {

}
