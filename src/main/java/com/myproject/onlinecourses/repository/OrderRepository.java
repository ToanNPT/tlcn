package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, String> {

    @Query("select o " +
            "from OrderDetail as o " +
            "where o.order.id = :orderId and o.course.id = :courseId")
    Optional<OrderDetail> getOrderDetailByOrderIdAndCourseId(String orderId, String courseId);

}
