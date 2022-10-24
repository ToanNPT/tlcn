package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.CouponConverter;
import com.myproject.onlinecourses.dto.CouponDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Coupon;
import com.myproject.onlinecourses.exception.DuplicateException;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.CouponRepository;
import com.myproject.onlinecourses.service.CouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CouponServiceImpl implements CouponService {

    @Autowired
    CouponRepository couponRepo;

    @Autowired
    CouponConverter converter;

    @Autowired
    AccountRepository accountRepo;

    @Override
    public ResponseObject getAllCoupon(){
        List<Coupon> couponList  = couponRepo.findAll();
        Collections.sort(couponList, new Comparator<Coupon>() {
            @Override
            public int compare(Coupon o1, Coupon o2) {
                if(o1.getCreateDate().equals(o2.getCreateDate()))
                    return 0;
                else if(o1.getCreateDate().after(o2.getCreateDate()))
                    return -1;
                else
                    return 1;
            }
        });
        List<CouponDTO> dtos = couponList.stream()
                .map(c -> converter.entityToDTO(c))
                .collect(Collectors.toList());
        return new ResponseObject(dtos);
    }

    @Override
    public ResponseObject getCouponById(String id){
        Optional<Coupon> coupon = couponRepo.findByCode(id);
        if(!coupon.isPresent())
            throw new NotFoundException("Not found coupon " + id);
        return new ResponseObject(converter.entityToDTO(coupon.get()));
    }

    @Override
    public ResponseObject deleteCoupon(String id){
        Optional<Coupon> coupon = couponRepo.findById(id);
        if(!coupon.isPresent())
            throw new NotFoundException("Not found coupon " + id);
        coupon.get().setActive(false);
        couponRepo.save(coupon.get());
        return new ResponseObject("", "200", "Delete successfull", null);
    }

    @Override
    public ResponseObject updateInfoCoupon(String id, CouponDTO dto){
        Optional<Coupon> coupon = couponRepo.findById(id);
        if(!coupon.isPresent())
            throw new NotFoundException("Not found coupon " + id);

        coupon.get().setDescription(dto.getDescription());
        coupon.get().setName(dto.getName());
        coupon.get().setCreateDate(dto.getCreateDate());
        coupon.get().setExpiredDate(dto.getExpiredDate());
        coupon.get().setStartDate(dto.getStartDate());
        coupon.get().setUpdateDate(new Date());
        coupon.get().setType(dto.getType());
        coupon.get().setValue(dto.getValue());
        coupon.get().setNum(dto.getNum());
        coupon.get().setNumberOfRemain(dto.getNumberOfRemain());

        Coupon res = couponRepo.save(coupon.get());
        return new ResponseObject(converter.entityToDTO(res));
    }

    @Override
    public ResponseObject addCoupon(CouponDTO dto){
        Optional<Coupon> coupon = couponRepo.findById(dto.getCode());
        if(coupon.isPresent())
            throw new DuplicateException("Duplicate code for coupon " + dto.getCode());
        Optional<Account> account = accountRepo.findById(dto.getUser_created());
        if(!account.isPresent())
            throw new NotFoundException("Not found account " + dto.getUser_created());
        Coupon entity  = converter.dtoToEntity(dto);
        entity.setCreateDate(new Date());
        entity.setAccount(account.get());
        Coupon res = couponRepo.save(entity);
        return new ResponseObject(converter.entityToDTO(res));
    }

    @Override
    public ResponseObject activeCoupon(String code){
        Optional<Coupon> coupon = couponRepo.findById(code);
        if(!coupon.isPresent())
            throw new NotFoundException("Not found coupon " + code);
        coupon.get().setActive(true);
        couponRepo.save(coupon.get());
        return new ResponseObject("", "200", "Active coupon successfully", null);
    }
}