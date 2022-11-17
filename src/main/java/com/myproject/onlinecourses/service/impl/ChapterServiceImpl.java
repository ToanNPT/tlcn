package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Chapter;
import com.myproject.onlinecourses.repository.ChapterRepository;
import com.myproject.onlinecourses.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterRepository chapterRepo;

    public ResponseObject getChaptersByCourse(String courseId){
        List<Chapter> chapters = chapterRepo.getChaptersByCourseId(courseId);
        if(chapters == null || chapters.size() == 0)
            return new ResponseObject(null);
        return new ResponseObject(null);

    }


    public void sort(List<Chapter> chapters){
        Optional<Integer> head = Optional.of(chapters.get(0).getNextChapterId());
        Optional<Integer> current = Optional.ofNullable(chapters.get(0).getId());
        int currentIndex = 0, headIndex = 0;

        while(current.isPresent()){
            if(head.get() != current.get()){
                currentIndex++;
                current = Optional.of(chapters.get(currentIndex).getId());
            }
        }

    }
}
