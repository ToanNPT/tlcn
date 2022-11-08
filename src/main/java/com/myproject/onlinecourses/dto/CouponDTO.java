package com.myproject.onlinecourses.dto;

import com.myproject.onlinecourses.entity.Account;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CouponDTO {
    private String code;
    private String name;
    private String description;
    private String type;
    private Double value;
    private Date expiredDate;
    private Date startDate;
    private Date createDate;
    private Date updateDate;
    private String user_created;
    private int num;
    private int numberOfRemain;
    private boolean isActive;
}
