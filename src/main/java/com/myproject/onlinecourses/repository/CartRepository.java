package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository<Cart, String> {

}
