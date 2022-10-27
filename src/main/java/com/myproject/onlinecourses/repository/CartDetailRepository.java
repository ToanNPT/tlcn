package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    @Query("select c " +
            "from CartDetail  c " +
            "where c.course.id = :courseId and c.cart.account.username = :username")
    Optional<CartDetail> getFirstByUsernameAndCourseId(String username, String courseId);
}
