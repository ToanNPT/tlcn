package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CoursesRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {

    @Query("select o.course.id from OrderDetail as o " +
            "where o.account.username = :username and o.course.id = :courseId and o.order.isActive = true")
    String checkPurchaseCourse(String username, String courseId);

    @Query("select o.course.id " +
            "from OrderDetail  as o " +
            "where o.account.username = :username and o.order.isActive = true")
    List<String> getListPurchasedCourse(String username);

    @Query(value = "select * " +
            "from courses as c " +
            "order by c.create_date desc " +
            "limit :limit", nativeQuery = true)
    List<Course> findNewestCourses(int limit);

    @Query(value = "select * " +
            "from courses  as c " +
            "order by c.num_students desc " +
            "limit :limit", nativeQuery = true)
    List<Course> findByTopNumStudents(int limit);
}