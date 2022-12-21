package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.dto.RevenuesByMonth;
import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.interfaceMapping.IRevenuesByInYear;
import com.myproject.onlinecourses.interfaceMapping.IRevenuesInMonth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface StatisticsRepository extends JpaRepository<Order, String> {

    @Query(value = "select month(o.create_date) as month, sum(o.payment_price) as value " +
            "from orders as o " +
            "where o.is_active = true and year(o.create_date) = ? " +
            "group by month(o.create_date) " +
            "order by month(o.create_date) asc", nativeQuery = true)
    List<IRevenuesByInYear> getRevenuesByMonths(String year);

    @Query(value = "select o.create_date as day, sum(o.payment_price) as value " +
            "from orders as o " +
            "where o.is_active = true and year(o.create_date) = :year and month(o.create_date) = :month " +
            "group by o.create_date " +
            "order by o.create_date asc", nativeQuery = true)
    List<IRevenuesInMonth> getRevenuesInMonth(Integer year, Integer month);

    @Query("select count(a) " +
            "from Account as a " +
            "where a.role.id = 'TEACHER'")
    Integer getTotalTeacher();

    @Query("select count(s) " +
            "from Account as s " +
            "where s.role.id = 'USER'")
    Integer getTotalStudent();

    @Query("select count(c) " +
            "from Course as c " +
            "where c.isActive = true")
    Integer getTotalCourses();
}
