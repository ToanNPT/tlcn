package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.ReviewDTO;
import com.myproject.onlinecourses.entity.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface ReviewConverter {

    @Mappings({
            @Mapping(target = "username", source = "entity.account.username"),
            @Mapping(target = "courseId", source = "entity.course.id")
    })
    ReviewDTO entityToDto(Review entity);

    @Mappings({
            @Mapping(target = "account", ignore = true ),
            @Mapping(target = "course", ignore = true),
            @Mapping(target = "createDate", ignore = true),
            @Mapping(target = "id",  ignore = true)
    })
    Review dtoToEntity(ReviewDTO dto);
}
