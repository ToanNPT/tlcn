package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.PaymentDTO;
import com.myproject.onlinecourses.entity.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface PaymentConverter {
    @Mappings(
            @Mapping(target = "active", source = "active")
    )
    PaymentDTO entityToDTO(Payment entity);

    @Mappings(
            @Mapping(target = "active", source = "active")
    )
    Payment dtoToEntity(PaymentDTO dto);
}
