package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, String> {

}

