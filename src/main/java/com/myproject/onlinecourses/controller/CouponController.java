package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.CouponDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.repository.CouponRepository;
import com.myproject.onlinecourses.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/v1/")
public class CouponController {
    @Autowired
    CouponService couponService;

    @GetMapping("coupons")
    public ResponseObject getAll(@RequestParam("page") Optional<Integer> page,
                                 @RequestParam("limit")Optional<Integer> limit) {
        return couponService.getAllCoupon(page, limit);
    }

    @GetMapping("coupon/{code}")
    public ResponseObject getById(@PathVariable("code") String code){
        return couponService.getCouponById(code);
    }

    @PostMapping("coupon/add-new")
    public ResponseObject addCoupon(@RequestBody CouponDTO dto){
        return couponService.addCoupon(dto);
    }

    @PutMapping("coupon/update/{code}")
    public ResponseObject updateInfoCoupon(@PathVariable("code") String code,
                                           @RequestBody CouponDTO dto){
        return couponService.updateInfoCoupon(code, dto);
    }

    @DeleteMapping("coupon/delete/{code}")
    public ResponseObject deleteCoupon(@PathVariable("code") String code){
        return couponService.deleteCoupon(code);
    }

    @GetMapping("coupon/active-code/{code}")
    public ResponseObject activeCoupon(@PathVariable("code") String code){
        return couponService.activeCoupon(code);
    }
}
