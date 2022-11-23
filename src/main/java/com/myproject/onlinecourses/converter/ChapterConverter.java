package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.ChapterDTO;
import com.myproject.onlinecourses.entity.Chapter;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ChapterConverter {

    @Mappings({
            @Mapping(target = "courseId", source = "course.id")
    }
    )
    ChapterDTO entityToDTO(Chapter chapter);
}
