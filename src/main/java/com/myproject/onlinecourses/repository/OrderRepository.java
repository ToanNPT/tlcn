package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
