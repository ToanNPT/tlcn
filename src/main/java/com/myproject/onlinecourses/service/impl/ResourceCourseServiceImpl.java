package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.ResourceConverter;
import com.myproject.onlinecourses.dto.ResourcesCourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.ResourcesCourse;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.ResourceCourseRepository;
import com.myproject.onlinecourses.service.ResourceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceCourseServiceImpl implements ResourceCourseService {

    @Autowired
    ResourceCourseRepository resourceCourseRepo;
    @Autowired
    ResourceConverter converter;

    @Override
    public ResponseObject getAllByCourseId(String courseId){
        List<ResourcesCourse> list = resourceCourseRepo.getAllByCourse_Id(courseId);
        return new ResponseObject(list.stream()
                .map(r -> converter.entityToDto(r)));
    }

    @Override
    public ResponseObject getResourceById(int id){
        Optional<ResourcesCourse> resource = resourceCourseRepo.findById(id);
        if(!resource.isPresent())
            throw new NotFoundException("Can not found resource " + id);
        return new ResponseObject(converter.entityToDto(resource.get()));
    }

    @Override
    public ResponseObject updateResource(int id, ResourcesCourseDTO dto){
        Optional<ResourcesCourse> resource = resourceCourseRepo.findById(id);
        if(!resource.isPresent())
            throw new NotFoundException("Can not found resource " + id);
        resource.get().setTitle(dto.getTitle());
        ResourcesCourse res = resourceCourseRepo.save(resource.get());
        return new ResponseObject(converter.entityToDto(res));
    }
}
