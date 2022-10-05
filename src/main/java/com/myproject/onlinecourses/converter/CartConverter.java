package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.CartDetailDTO;
import com.myproject.onlinecourses.dto.CartDto;
import com.myproject.onlinecourses.entity.Cart;
import com.myproject.onlinecourses.entity.CartDetail;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CartConverter {

    @Mappings(
            @Mapping(target = "courseId", source = "course.id")
    )
    CartDetailDTO entityToCartDetailDTO(CartDetail dto);

    @Mappings(
            @Mapping(target = "cartDetailList", ignore = true)
    )
    CartDto entityToCartDTO(Cart cart);
}
