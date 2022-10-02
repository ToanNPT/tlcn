package com.myproject.onlinecourses.utils;

import com.myproject.onlinecourses.entity.Course_;

import java.util.HashMap;
import java.util.Map;

public class FilterParam {
    public static final Map<String, String> courses_filter =  Map.ofEntries(
            Map.entry("l", Course_.LANGUAGE), Map.entry("c", Course_.CATEGORY),
            Map.entry("name", Course_.NAME), Map.entry("min", Course_.PRICE),
            Map.entry("max", Course_.PRICE), Map.entry("num", Course_.NUM_STUDENTS),
            Map.entry("f", Course_.IS_PUBLIC));
}
