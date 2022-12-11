package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Cart;
import com.myproject.onlinecourses.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, String> {
    @Query("select c " +
            "from CartDetail as c " +
            "where c.cart.username = :username and c.course.id = :course")
    Optional<Course> checkExitedCourseInCart(String course, String username);

    @Modifying
    @Query(value = "delete " +
            "from cartdetail  as c " +
            "where c.user_name = :username and c.course_id in (:params) ", nativeQuery = true)
    void deleteItemInCartByListCourseId(String username, String params );
}
