package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.CouponDTO;
import com.myproject.onlinecourses.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CouponConverter {

    @Mapping(target = "user_created", source = "coupon.account.username")
    @Mapping(target = "active", source = "active")
    CouponDTO entityToDTO (Coupon coupon);

    @Mappings({
            @Mapping(target = "account", ignore = true),
            @Mapping(target = "active", source = "active")
    })
    Coupon dtoToEntity(CouponDTO dto);
}
