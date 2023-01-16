package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CoursesRepository extends JpaRepository<Course, String>, JpaSpecificationExecutor<Course> {

    @Query("select o.course.id from OrderDetail as o " +
            "where o.account.username = :username and o.course.id = :courseId and o.order.isActive = true")
    String checkPurchaseCourse(@Param("username") String username,
                               @Param("courseId") String courseId);

    @Query("select o.course.id " +
            "from OrderDetail  as o " +
            "where o.account.username = :username and o.order.isActive = true")
    List<String> getListPurchasedCourse(@Param("username") String username);

    @Query(value = "select * " +
            "from courses as c " +
            "order by c.create_date desc " +
            "limit :limit", nativeQuery = true)
    List<Course> findNewestCourses(@Param("limit") int limit);

    @Query(value = "select * " +
            "from courses  as c " +
            "order by c.num_students desc " +
            "limit :limit", nativeQuery = true)
    List<Course> findByTopNumStudents(@Param("limit") int limit);

    @Query("select c " +
            "from Course as c " +
            "where c.id = :id and c.isActive = true")
    Optional<Course> findById(@Param("id") String id);

    @Query("select c " +
            "from Course as c " +
            "where c.isActive = true " +
            "order by c.createDate desc")
    Page<Course> findAll(Pageable pageable);

    @Query("select c.price " +
            "from Course as c " +
            "where c.id = :id and c.isActive = true")
    Double getPriceInCourse(@Param("id") String id);
}
