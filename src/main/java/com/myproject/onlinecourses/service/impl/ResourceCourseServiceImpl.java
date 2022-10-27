package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.aws.AwsS3Service;
import com.myproject.onlinecourses.converter.ResourceConverter;
import com.myproject.onlinecourses.dto.ResourceDTO;
import com.myproject.onlinecourses.dto.ResourcesCourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.entity.ResourcesCourse;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.repository.ResourceCourseRepository;
import com.myproject.onlinecourses.service.ResourceCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceCourseServiceImpl implements ResourceCourseService {

    @Autowired
    ResourceCourseRepository resourceCourseRepo;
    @Autowired
    ResourceConverter converter;

    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    AwsS3Service awsS3Service;

    @Value("${amazonProperties.resource.bucketName}")
    private String resourceBucket;

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

    @Override
    public ResponseObject addResource(String username, String courseId, ResourceDTO dto){
        Optional<Course> course = coursesRepo.findById(courseId);
        Optional<Account> account =accountRepo.findById(username);
        if(!course.isPresent() || !account.isPresent()){
            throw new NotFoundException("Something was wrong when try adding file");
        }
        String filename = account.get().getUsername()
                + "-" + course.get().getName()
                + "-" + dto.getName();
        String url = awsS3Service.uploadSingleFile(resourceBucket, filename, dto.getFile());

        ResourcesCourse resource = new ResourcesCourse();
        resource.setTitle(dto.getName());
        resource.setCourse(course.get());
        resource.setLink(url);

        ResourcesCourse res = resourceCourseRepo.save(resource);
        return new ResponseObject(converter.entityToDto(res));
    }
}
