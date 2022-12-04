package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.ResponseObject;

public interface StatisticsService {
    ResponseObject getRevenuesInYear(String year);
}
