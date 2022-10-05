package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.CategoryDTO;
import com.myproject.onlinecourses.entity.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryConverter {

    CategoryDTO entityToDTO(Category category);

    @Mapping(target = "active", ignore = true)
    Category dtoToEntity(CategoryDTO dto);
}
