package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.CourseDTO;
import com.myproject.onlinecourses.entity.Course;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CourseConverter {

    @Mappings ({
            @Mapping(target = "accountName", source = "course.account.username"),
            @Mapping(target = "active", source = "course.active")
    })
    CourseDTO entityToCourseDTO(Course course);

    @Mappings({
            @Mapping(target = "account", ignore = true ),
            @Mapping(target = "numStudents", ignore = true),
            @Mapping(target = "active", source = "dto.active")
    })
    Course courseDtoToEntity(CourseDTO dto);
}
