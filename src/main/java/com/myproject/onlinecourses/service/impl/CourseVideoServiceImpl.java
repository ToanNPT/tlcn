package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.CourseVideoConverter;
import com.myproject.onlinecourses.dto.CourseVideoDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.CoursesVideo;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.CourseVideoRepository;
import com.myproject.onlinecourses.service.CourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CourseVideoServiceImpl implements CourseVideoService {

    @Autowired
    CourseVideoRepository courseVideoRepo;

    @Autowired
    CourseVideoConverter converter;

    @Override
    public ResponseObject getVideosByCourseId(String courseId){
        List<CoursesVideo> coursesVideo = courseVideoRepo.findAllByCourse_Id(courseId);
        return new ResponseObject(coursesVideo.stream()
                .map(c -> converter.entityToDTO(c))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseObject getVideosByChapter(String chapter){
        List<CoursesVideo> coursesVideos = courseVideoRepo.findAllByChapter(chapter);
        return new ResponseObject(coursesVideos.stream()
                .map(c -> converter.entityToDTO(c))
                .collect(Collectors.toList()));
    }

    @Override
    public ResponseObject addNewVideo(CourseVideoDTO dto){
        return null;
    }

    @Override
    public ResponseObject updateInfoVideo(int id, CourseVideoDTO dto){
        Optional<CoursesVideo> video = courseVideoRepo.findById(id);
        if(!video.isPresent()) throw new NotFoundException("Can not find video");
        video.get().setChapter(dto.getChapter());
        video.get().setDescription(dto.getDescription());
        video.get().setUpdateDate(new Date());
        video.get().setTitle(dto.getTitle());
        CoursesVideo res =  courseVideoRepo.save(video.get());
        return new ResponseObject(converter.entityToDTO(res));
    }

    @Override
    public ResponseObject deleteCourseVideo(int id){
        Optional<CoursesVideo> video = courseVideoRepo.findById(id);
        if(!video.isPresent()) throw new NotFoundException("Can not find video");
        video.get().setActive(false);
        return new ResponseObject("", "200", "Delete successful", null);
    }

}
