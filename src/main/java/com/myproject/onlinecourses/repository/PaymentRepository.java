package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment,String > {

    @Query("select p from Payment p where p.isActive = :isActive")
    List<Payment> findAllByActive(boolean isActive);

    @Query("select p from Payment p where p.id = :id and p.isActive = true")
    Optional<Payment> findById(String id);

    @Query("select p from Payment p where p.isActive = true and p.name like :name")
    Optional<Payment> findByName(String name);
}
