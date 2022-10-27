package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.ResourceDTO;
import com.myproject.onlinecourses.dto.ResourcesCourseDTO;
import com.myproject.onlinecourses.entity.ResourcesCourse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ResourceConverter {

    @Mapping(target = "courseId", source = "entity.course.id")
    @Mapping(target = "title", source = "entity.title")
    ResourcesCourseDTO entityToDto(ResourcesCourse entity);

    @Mapping(target = "course", ignore = true)
    @Mapping(target = "title", source = "dto.name")
    ResourcesCourse dtoToEntity(ResourceDTO dto);
}
