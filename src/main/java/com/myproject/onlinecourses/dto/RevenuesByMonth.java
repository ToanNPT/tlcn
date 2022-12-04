package com.myproject.onlinecourses.dto;

import lombok.*;
import lombok.experimental.Helper;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevenuesByMonth {
    private String month;
    private double value;

}
