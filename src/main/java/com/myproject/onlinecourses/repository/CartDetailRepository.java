package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {
}
