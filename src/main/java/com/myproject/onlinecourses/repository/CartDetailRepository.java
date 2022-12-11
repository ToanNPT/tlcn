package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Cart;
import com.myproject.onlinecourses.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    @Query("select c " +
            "from CartDetail  c " +
            "where c.course.id = :courseId and c.cart.account.username = :username")
    Optional<CartDetail> getFirstByUsernameAndCourseId(String username, String courseId);

    @Query("select c " +
            "from CartDetail c " +
            "where c.cart.username = :username")
    List<CartDetail> findAllByUsername(String username);
}
