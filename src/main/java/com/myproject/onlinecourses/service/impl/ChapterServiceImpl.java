package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.ChapterConverter;
import com.myproject.onlinecourses.dto.ChapterDTO;
import com.myproject.onlinecourses.dto.NewChapterDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Chapter;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.ChapterRepository;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ChapterServiceImpl implements ChapterService {
    @Autowired
    ChapterRepository chapterRepo;

    @Autowired
    ChapterConverter converter;

    @Autowired
    CoursesRepository coursesRepo;

    @Override
    public ResponseObject getChapterById(Integer id){
        Optional<Chapter> found = chapterRepo.findById(id);
        if(!found.isPresent())
            throw new NotFoundException("Can not found chapter");
        return new ResponseObject(converter.entityToDTO(found.get()));
    }

    @Override
    public ResponseObject getChaptersByCourse(String courseId){
        List<Chapter> chapters = chapterRepo.getChaptersByCourseId(courseId);
        if(chapters == null || chapters.size() == 0)
            return new ResponseObject(null);
        List<ChapterDTO> dtos = chapters.stream()
                .map(chapter -> converter.entityToDTO(chapter))
                .collect(Collectors.toList());
        if(chapters.size() <=2 )
            return new ResponseObject(dtos);
        this.sort(dtos);
        return new ResponseObject(dtos);
    }

    @Override
    public ResponseObject addNewChapter(NewChapterDTO dto){
        Optional<Course> course = coursesRepo.findById(dto.getCourseId());
        if(!course.isPresent())
            throw new NotFoundException("Can not found course " + dto.getCourseId());
        Chapter newChap = new Chapter();
        newChap.setCourse(course.get());
        newChap.setChapterName(dto.getChapterName());
        newChap.setNumVideos(0);
        newChap.setNextChapterId(dto.getNextChapterId());

        Chapter result = setChapter(newChap);
        return new ResponseObject(converter.entityToDTO(result));

    }

    @Override
    public ResponseObject updateChapterInfor(Integer chapterId, NewChapterDTO dto){
        Optional<Course> course = coursesRepo.findById(dto.getCourseId());
        if(!course.isPresent())
            throw new NotFoundException("Can not found course " + dto.getCourseId());

        Optional<Chapter> updateChap = chapterRepo.findById(chapterId);
        if(!updateChap.isPresent())
            throw new NotFoundException("Can not found chapter " + chapterId);

        updateChap.get().setCourse(course.get());
        updateChap.get().setChapterName(dto.getChapterName());

        int chapterSize = chapterRepo.countChaptersInCourse(dto.getCourseId());
        if(chapterSize == 1 || dto.getNextChapterId() == updateChap.get().getNextChapterId()){
            Chapter updated = chapterRepo.save(updateChap.get());
            return new ResponseObject(converter.entityToDTO(updated));
        }
        else{
            Chapter updated = updatePositionChap(updateChap.get(), dto.getNextChapterId());
            return new ResponseObject(converter.entityToDTO(updated));
        }
    }

    @Override
    public ResponseObject deleteChapter(Integer chapterId){
        Optional<Chapter> found = chapterRepo.findById(chapterId);
        if(!found.isPresent())
            throw new NotFoundException("Can not found chapter");
        List<Chapter> chapters = chapterRepo.getChaptersByCourseId(found.get().getCourse().getId());
        if(chapters.size() == 1){
            chapterRepo.delete(found.get());
            return new ResponseObject("", "200", "Delete successful", null);
        }
        if(found.get().isHeadChapter()){
            if(found.get().getNextChapterId() == -1){
                chapterRepo.delete(found.get());
                return new ResponseObject("", "200", "Delete successful", null);
            }
            Optional<Chapter> after = chapterRepo.findById(found.get().getNextChapterId());
            after.get().setHeadChapter(true);
            chapterRepo.save(after.get());
            chapterRepo.delete(found.get());
            return new ResponseObject("", "200", "Delete successful", null);
        }
        else if(found.get().getNextChapterId() == -1){
            Optional<Chapter> before = chapterRepo.getChapterByNextId(found.get().getCourse().getId(),
                    found.get().getId());
            before.get().setNextChapterId(-1);
            chapterRepo.save(before.get());
            chapterRepo.delete(found.get());
            return new ResponseObject("", "200", "Delete successful", null);
        }
        else if(found.get().getNextChapterId() != 1 && !found.get().isHeadChapter()){
            Optional<Chapter> before = chapterRepo.getChapterByNextId(found.get().getCourse().getId(),
                    found.get().getId());
            before.get().setNextChapterId(found.get().getNextChapterId());
            chapterRepo.save(before.get());
            chapterRepo.delete(found.get());
            return new ResponseObject("", "200", "Delete successful", null);
        }
        else{
            return new ResponseObject("400", "200", "Something error", null);
        }
    }


    public Chapter setChapter(Chapter chapter){
        List<Chapter> list = chapterRepo.getChaptersByCourseId(chapter.getCourse().getId());
        if(list.size() == 0){
            chapter.setNextChapterId(-1);
            chapter.setHeadChapter(true);
            return chapterRepo.save(chapter);
        }
        else if(chapter.getNextChapterId() == -1 && list.size() != 0){
            chapter.setHeadChapter(false);
            chapter.setNextChapterId(-1);
            Optional<Chapter> currentChap = chapterRepo.getChapterByNextId(chapter.getCourse().getId(), -1);
            Chapter savedChap = chapterRepo.save(chapter);
            currentChap.get().setNextChapterId(savedChap.getId());
            chapterRepo.save(currentChap.get());
            return savedChap;
        }
        else {
            Optional<Chapter> nextChapter = chapterRepo.findById(chapter.getNextChapterId());
            if(!nextChapter.isPresent())
                throw new NotFoundException("Can not found next chapter");
            if(nextChapter.get().isHeadChapter()){
                nextChapter.get().setHeadChapter(false);
                chapter.setHeadChapter(true);
                chapterRepo.save(nextChapter.get());
                return chapterRepo.save(chapter);
            }
            else {
                Optional<Chapter> beforeChap = chapterRepo.getChapterByNextId(nextChapter.get().getCourse().getId()
                        ,nextChapter.get().getId());
                chapter.setHeadChapter(false);
                Chapter inserted = chapterRepo.save(chapter);
                beforeChap.get().setNextChapterId(inserted.getId());
                chapterRepo.save(beforeChap.get());
                return inserted;
            }
        }
    }

    public Chapter updatePositionChap(Chapter chapter, int newNextChap){
        if(chapter.isHeadChapter()){
            Optional<Chapter> afterChapter = chapterRepo.findById(chapter.getNextChapterId());
            afterChapter.get().setHeadChapter(true);
            chapterRepo.save(afterChapter.get());
        }
        else if(chapter.getNextChapterId() == -1){
            Optional<Chapter> beforeChap = chapterRepo.getChapterByNextId(chapter.getCourse().getId(),
                    chapter.getId());
            beforeChap.get().setNextChapterId(-1);
        }
        else if(chapter.getNextChapterId() != -1 && !chapter.isHeadChapter()){
            Optional<Chapter> beforeChap = chapterRepo.getChapterByNextId(chapter.getCourse().getId(),
                    chapter.getId());
            beforeChap.get().setNextChapterId(chapter.getNextChapterId());
            chapterRepo.save(beforeChap.get());
        }
        chapter.setNextChapterId(newNextChap);
        return setChapter(chapter);
    }

    static public void sort(List<ChapterDTO> chapters){
        int index = 0, nextIndex = 1, size = chapters.size();
        while(index < size && index < size && nextIndex < size){
            int nextNodeId = chapters.get(index).getNextChapterId();
            int currentNodeId = chapters.get(nextIndex).getId();
            if(currentNodeId != nextNodeId)
                nextIndex ++;
            else if(currentNodeId == nextNodeId && nextIndex - index > 1){
                ChapterDTO tg = null;
                tg = chapters.get(index +1);
                chapters.set(index + 1, chapters.get(nextIndex));
                chapters.set(nextIndex, tg );
                index++;
                nextIndex = index +1;
            }
            else{
                index ++;
                nextIndex = index +1;
            }
        }
    }
}
