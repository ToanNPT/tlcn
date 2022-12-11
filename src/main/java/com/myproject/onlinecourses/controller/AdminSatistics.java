package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class AdminSatistics {

    @Autowired
    StatisticsService service;

    @GetMapping("reports/revenues/{year}")
    public ResponseObject getRevenuesInYear(@PathVariable("year") String year){
        return service.getRevenuesInYear(year);
    }
    @GetMapping("reports/revenues/{year}/{month}")
    public ResponseObject getRevenuesInMonth(@PathVariable("year") Integer year,
                                             @PathVariable("month") Integer month){
        return service.statisticRevenuesInMonth(year, month);
    }

}
