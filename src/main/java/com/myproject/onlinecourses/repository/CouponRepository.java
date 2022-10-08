package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, String> {
    @Query("select c from Coupon as c where c.code = :code and c.isActive = true")
    Optional<Coupon> findByCode(String code);
}
