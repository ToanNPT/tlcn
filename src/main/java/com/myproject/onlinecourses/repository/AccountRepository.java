package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

    @Query("select a " +
            "from Account as a " +
            "where a.userDetail.email = :email")
    Optional<Account> findAccountByEmail(@Param("email") String email);
}

