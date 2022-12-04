package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.dto.RevenuesByMonth;
import com.myproject.onlinecourses.entity.Order;
import com.myproject.onlinecourses.interfaceMapping.IRevenuesByInYear;
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
}
