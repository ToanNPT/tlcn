package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.CourseVideoDTO;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.entity.CoursesVideo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CourseVideoConverter {

    @Mappings({
            @Mapping(target = "courseId", source = "course.id"),
            @Mapping(target = "active", source = "active")
    })
    public CourseVideoDTO entityToDTO(CoursesVideo entity);

    @Mappings({
            @Mapping(target = "course", ignore = true),
            @Mapping(target = "active", source = "active")
    })
    public CoursesVideo dtoToEntity(CourseVideoDTO dto);

}
