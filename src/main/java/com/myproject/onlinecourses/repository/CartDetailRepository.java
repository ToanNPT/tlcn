package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Cart;
import com.myproject.onlinecourses.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
    @Query("select c " +
            "from CartDetail  c " +
            "where c.course.id = :courseId and c.cart.account.username = :username")
    Optional<CartDetail> getFirstByUsernameAndCourseId(@Param("username")String username,
                                                       @Param("courseId") String courseId);

    @Query("select c " +
            "from CartDetail c " +
            "where c.cart.username = :username")
    List<CartDetail> findAllByUsername(@Param("username") String username);

    @Modifying
    @Query("delete from CartDetail as c where c.course.id = :courseId and c.cart.username = :username")
    void deleteByUsernameAndCourseId(@Param("username") String username,
                                     @Param("courseId") String courseId);
}
