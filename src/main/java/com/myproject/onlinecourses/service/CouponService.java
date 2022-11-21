package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.CouponDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface CouponService {
    ResponseObject getAllCoupon(Optional<Integer> page, Optional<Integer> limit);

    ResponseObject getCouponById(String id);

    ResponseObject deleteCoupon(String id);

    ResponseObject updateInfoCoupon(String id, CouponDTO dto);

    ResponseObject addCoupon(CouponDTO dto);

    ResponseObject activeCoupon(String code);

    boolean isCouponValid(String code);
}
