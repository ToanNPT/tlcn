package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.dto.OverallDashBoard;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.RevenuesByMonth;
import com.myproject.onlinecourses.dto.RevenuesInMonth;
import com.myproject.onlinecourses.interfaceMapping.IRevenuesByInYear;
import com.myproject.onlinecourses.interfaceMapping.IRevenuesInMonth;
import com.myproject.onlinecourses.repository.StatisticsRepository;
import com.myproject.onlinecourses.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.time.YearMonth;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {

    @Autowired
    StatisticsRepository repository;
    private Class<?> mfogof;

    @Override
    public ResponseObject getRevenuesInYear(String year) {
        List<IRevenuesByInYear> revenues = repository.getRevenuesByMonths(year);
        List<RevenuesByMonth> resultList = new ArrayList<>();
        for (int i = 1; i <= 12; i++) {
            RevenuesByMonth value = new RevenuesByMonth(String.valueOf(i), 0);
            resultList.add(value);
        }

        for (IRevenuesByInYear entry : revenues) {
            resultList.get(Integer.parseInt(entry.getMonth()) - 1).setValue(entry.getValue());
        }

        return new ResponseObject(resultList);
    }

    @Override
    public ResponseObject statisticRevenuesInMonth(Integer year, Integer month) {
        Map<String, Double> revenuesByDate = new HashMap<>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month - 1);
        int maxDays = calendar.getActualMaximum(Calendar.MONTH);

        YearMonth ym = YearMonth.of(year, month);
        LocalDate firstOfMonth = ym.atDay(1);
        LocalDate firstOfFollowingMonth = ym.plusMonths(1).atDay(1);
        Map<String, Double> revenues = new HashMap<>();

        List<LocalDate> dates = firstOfMonth.datesUntil(firstOfFollowingMonth)
                .collect(Collectors.toList());


        for(LocalDate d : dates){
            revenues.put(d.toString(), 0.0);
        }

        List<IRevenuesInMonth> inMonth = repository.getRevenuesInMonth(year, month);

        inMonth.forEach(value -> {
            revenues.put(value.getDay(), value.getValue());
        });

        return new ResponseObject(revenues.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(p -> new RevenuesInMonth(p.getKey(), p.getValue())));
    }

    @Override
    public ResponseObject overviewDashBoard(){
        int studentNums = repository.getTotalStudent();
        int teacherNums = repository.getTotalTeacher();
        int coursesNums = repository.getTotalCourses();

        OverallDashBoard res = new OverallDashBoard(studentNums, teacherNums, coursesNums, 0);
        return new ResponseObject(res);
    }

}
