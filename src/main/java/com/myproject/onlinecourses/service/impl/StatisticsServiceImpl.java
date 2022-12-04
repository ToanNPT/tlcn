package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.RevenuesByMonth;
import com.myproject.onlinecourses.interfaceMapping.IRevenuesByInYear;
import com.myproject.onlinecourses.repository.StatisticsRepository;
import com.myproject.onlinecourses.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsRepository repository;

    @Override
    public ResponseObject getRevenuesInYear(String year){
        List<IRevenuesByInYear> revenues = repository.getRevenuesByMonths(year);
        List<RevenuesByMonth> resultList = new ArrayList<>();
        for(int i =1; i <= 12; i++){
            RevenuesByMonth value = new RevenuesByMonth(String.valueOf(i), 0);
            resultList.add(value);
        }

        for(IRevenuesByInYear entry: revenues){
            resultList.get(Integer.parseInt(entry.getMonth()) -1).setValue(entry.getValue());
        }

        return new ResponseObject(resultList);
    }
}
