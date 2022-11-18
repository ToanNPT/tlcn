package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.aws.AwsS3Service;
import com.myproject.onlinecourses.converter.CourseConverter;
import com.myproject.onlinecourses.dto.CourseDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.dto.SearchCriteria;
import com.myproject.onlinecourses.dto.UploadCourse;
import com.myproject.onlinecourses.entity.*;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.AccountRepository;
import com.myproject.onlinecourses.repository.CategoryRepository;
import com.myproject.onlinecourses.repository.CoursePaidRepository;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.service.CoursesService;
import com.myproject.onlinecourses.specification.CoursesSpecification;
import com.myproject.onlinecourses.utils.FilterParam;
import com.myproject.onlinecourses.utils.PaginationConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CoursesServiceImpl implements CoursesService {
    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    CategoryRepository categoryRepos;
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    CategoryRepository getCategoryRepo;

    @Autowired
    CourseConverter converter;

    @Autowired
    AwsS3Service awsS3Service;

    @Value("${amazonProperties.avatarCourse.bucketName}")
    private String avatarBucket;

    @Autowired
    CoursePaidRepository coursePaidRepo;

    @Override
    public ResponseObject getById(String id){
        Optional<Course> course = coursesRepo.findById(id);
        if(!course.isPresent()){
            throw new NotFoundException("Can not found course");
        }
        return new ResponseObject(converter.entityToCourseDTO(course.get()));
    }

    @Override
    public ResponseObject getAll(Optional<Integer> page){
        Pageable pageable = PageRequest.of(page.orElse(0), PaginationConstants.COURSES_PAGE_SIZE);
        Page<Course> coursesList = coursesRepo.findAll(pageable);
        Page<CourseDTO> courseDTOS = coursesList.map(new Function<Course, CourseDTO>() {
            @Override
            public CourseDTO apply(Course course) {
                return converter.entityToCourseDTO(course);
            }
        });
        return new ResponseObject(courseDTOS);
    }

    @Override
    public ResponseObject filterCourses(List<SearchCriteria> conditions){
        CoursesSpecification specification = new CoursesSpecification(categoryRepos);
        for(SearchCriteria criteria : conditions){
            specification.add(criteria);
        }
        List<Course> courses = coursesRepo.findAll(specification);
        List<CourseDTO> courseDTOS = courses.stream()
                .map(c -> converter.entityToCourseDTO(c)).collect(Collectors.toList());
        return new ResponseObject(courseDTOS);
    }

    @Override
    public ResponseObject saveCourse(UploadCourse dto){

        Course course = converter.uploadToEntity(dto);
        Optional<Account> account = accountRepository.findById(dto.getAccountName());
        Optional<Category> category = categoryRepos.findById(dto.getCategory());

        if(!account.isPresent() || !category.isPresent())
            throw new NotFoundException("Data not valid");

        String filename = account.get().getUsername() + "-" + dto.getName();
        String url = awsS3Service.uploadSingleFile(avatarBucket, filename, dto.getAvatar());

        course.setAccount(account.get());
        course.setNumStudents(0);
        course.setAvatar(url);
        course.setCategory(category.get());
        course.setCreateDate(new Date());
        course.setUpdateDate(null);

        Course res = coursesRepo.save(course);
        return new ResponseObject(converter.entityToCourseDTO(res));
    }

    @Override
    public ResponseObject delete(String courseId){
        Optional<Course> course = coursesRepo.findById(courseId);
        if(!course.isPresent()){
            throw new NotFoundException("Can not found course");
        }
        course.get().setActive(false);
        coursesRepo.save(course.get());
        return new ResponseObject("", "200", "Delete successful", null);
    }

    @Override
    public ResponseObject update(String id, CourseDTO dto){
        Optional<Course> c = coursesRepo.findById(id);
        if(!c.isPresent()) throw new NotFoundException("Can not found course");

        Course updateEntity = converter.courseDtoToEntity(dto);
        updateEntity.setId(id);
        updateEntity.setAccount(c.get().getAccount());
        updateEntity.setNumStudents(dto.getNumStudents());
        Course res = coursesRepo.save(updateEntity);
        return new ResponseObject("", "200", "Update successful", converter.entityToCourseDTO(res));
    }

    @Override
    public ResponseObject checkPurchaseCourse(String username, String courseId){
        Optional<CoursePaid> purchaseCourse = coursePaidRepo.isPaid(courseId, username);

        if(purchaseCourse.isPresent())
            return new ResponseObject(true);
        return new ResponseObject(false);
    }

    @Override
    public ResponseObject getListPurchasedCourse(String username){
        List<CoursePaid> listPurchasedCourses = coursePaidRepo.getCoursesPaidByUsername(username);
        return new ResponseObject(listPurchasedCourses.stream()
                .map((coursePaid -> coursePaid.getCourse().getId())));
    }
}
