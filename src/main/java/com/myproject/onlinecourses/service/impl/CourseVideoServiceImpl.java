package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.aws.AwsS3Service;
import com.myproject.onlinecourses.converter.ChapterConverter;
import com.myproject.onlinecourses.converter.CourseVideoConverter;
import com.myproject.onlinecourses.dto.*;
import com.myproject.onlinecourses.entity.Chapter;
import com.myproject.onlinecourses.entity.Course;
import com.myproject.onlinecourses.entity.CoursesVideo;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.ChapterRepository;
import com.myproject.onlinecourses.repository.CourseVideoRepository;
import com.myproject.onlinecourses.repository.CoursesRepository;
import com.myproject.onlinecourses.service.CourseVideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class CourseVideoServiceImpl implements CourseVideoService {

    @Autowired
    CourseVideoRepository courseVideoRepo;

    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    ChapterRepository chapterRepo;

    @Autowired
    AwsS3Service s3Service;

    @Autowired
    CourseVideoConverter converter;

    @Autowired
    ChapterConverter chapterConverter;

    @Value("${amazonProperties.videoBucket.bucketName}")
    private String bucketVideo;

    @Override
    public ResponseObject getVideosByCourseId(String courseId){
        List<Chapter> chapters = chapterRepo.getChaptersByCourseId(courseId);
        List<ChapterDTO> dtos = chapters.stream()
                .map(p -> chapterConverter.entityToDTO(p))
                .collect(Collectors.toList());

        ChapterServiceImpl.sort(dtos);
        List<CourseVideoDTO> videos = new ArrayList<>();
        for(ChapterDTO dto : dtos){
            List<CoursesVideo> videoInChapter = courseVideoRepo.getVideosByChapterId(dto.getId());
            List<CourseVideoDTO> videoDTOS = videoInChapter.stream()
                    .map(p -> converter.entityToDTO(p))
                    .collect(Collectors.toList());
            this.sort(videoDTOS);
            videos.addAll(videoDTOS);
        }
        return new ResponseObject(videos);
    }


    @Override
    public ResponseObject getAll(){
        List<CoursesVideo> coursesVideoList = courseVideoRepo.findAll();
        List<CourseVideoDTO> dtoList = coursesVideoList.stream()
                .map(video -> converter.entityToDTO(video))
                .collect(Collectors.toList());
        return new ResponseObject(dtoList);
    }

    @Override
    public ResponseObject getCourseVideoById(Integer id){
        Optional<CoursesVideo> video = courseVideoRepo.findById(id);
        if(!video.isPresent())
            throw new NotFoundException("Can not found video " + id);
        return new ResponseObject(converter.entityToDTO(video.get()));
    }

    @Override
    public ResponseObject getVideosByChapter(Integer chapterId){
        List<CoursesVideo> coursesVideos = courseVideoRepo.getVideosByChapterId(chapterId);
        List<CourseVideoDTO> dtos = coursesVideos.stream()
                .map(p -> converter.entityToDTO(p))
                .collect(Collectors.toList());
        sort(dtos);
        return new ResponseObject(dtos);
    }

    @Override
    public ResponseObject addNewVideo(UploadVideoDTO dto, String username, String courseId, Integer chapterId){
        Optional<Course> course = coursesRepo.findById(courseId);
        if(!course.isPresent() )
            throw new NotFoundException("Can not find course id");
        if(dto.getVideo() == null)
            throw new NotFoundException("Video is not attached");

        Optional<Chapter> chapter = chapterRepo.findById(chapterId);
        if(!chapter.isPresent())
            throw new NotFoundException("Can not find chapter");

        String filename = username + "-" + courseId + "-" + chapter.get().getId() + "-" + dto.getTitle();
        String url = s3Service.uploadSingleFile(bucketVideo, filename, dto.getVideo());

        if(url == null ) throw new RuntimeException(" Error Internal Server");


        CoursesVideo coursesVideo = new CoursesVideo();
        coursesVideo.setActive(true);
        coursesVideo.setCourse(course.get());
        coursesVideo.setTitle(dto.getTitle());
//        coursesVideo.setChapter(dto.getChapter());
        coursesVideo.setDescription(dto.getDescription());
        coursesVideo.setNextVideoId(dto.getNextVideoId());
        coursesVideo.setCreateDate(new Date());
        coursesVideo.setChapter(chapter.get());
        coursesVideo.setLink(url);

        CoursesVideo inserted = setPositionVideo(coursesVideo);

        return new ResponseObject(converter.entityToDTO(inserted));
    }

    @Override
    public ResponseObject updateInfoVideo(int videoId, UpdateInforVideo form){
        Optional<CoursesVideo> video = courseVideoRepo.findById(videoId);
        if(!video.isPresent()) throw new NotFoundException("Can not find video");

        video.get().setDescription(form.getDescription());
        video.get().setUpdateDate(new Date());
        video.get().setTitle(form.getTitle());

        if(video.get().getNextVideoId() != form.getNextVideoId()){
            CoursesVideo updated = updatePositionVideo(video.get(), form.getNextVideoId());
            return new ResponseObject(converter.entityToDTO(updated));
        }
        else{
            CoursesVideo res =  courseVideoRepo.save(video.get());
            return new ResponseObject(converter.entityToDTO(res));
        }

    }

    @Override
    public ResponseObject deleteCourseVideo(int id){
        Optional<CoursesVideo> video = courseVideoRepo.findById(id);
        if(!video.isPresent()) throw new NotFoundException("Can not find video");
        int size = courseVideoRepo.countVideosInChapter(video.get().getChapter().getId());
        video.get().setActive(false);
        if(size == 1){
            return new ResponseObject("", "200", "Delete successful", null);
        }
        else if(video.get().isHeadVideo() ){
            Optional<CoursesVideo> after = courseVideoRepo.findById(video.get().getNextVideoId());
            after.get().setHeadVideo(true);
            courseVideoRepo.save(after.get());
            return new ResponseObject("", "200", "Delete successful", null);
        }
        else if(video.get().getNextVideoId() == -1){
            Optional<CoursesVideo>  before = courseVideoRepo.findByNextChapterId(video.get().getChapter().getId(),
                    video.get().getId());
            before.get().setNextVideoId(-1);
            courseVideoRepo.save(before.get());
            return new ResponseObject("", "200", "Delete successful", null);
        }
        else{
            Optional<CoursesVideo>  before = courseVideoRepo.findByNextChapterId(video.get().getChapter().getId(),
                    video.get().getId());
            before.get().setNextVideoId(video.get().getNextVideoId());
            courseVideoRepo.save(before.get());
            return new ResponseObject("", "200", "Delete successful", null);
        }
    }

    public void sort(List<CourseVideoDTO> videos){
        int index = 0, nextIndex = 1, size = videos.size();
        while(index < size && nextIndex < size){
            int nextNodeId = videos.get(index).getNextVideoId(),
                    currentNodeId = videos.get(nextIndex).getId();

            if(currentNodeId != nextNodeId)
                nextIndex ++;
            else if(currentNodeId == nextNodeId && nextIndex - index > 1){
                CourseVideoDTO tg = null;
                tg = videos.get(index +1);
                videos.set(index + 1, videos.get(nextIndex));
                videos.set(nextIndex, tg );
                index++;
                nextIndex = index +1;
            }
            else{
                index ++;
                nextIndex = index +1;
            }
        }
    }

    public CoursesVideo setPositionVideo(CoursesVideo video){
        List<CoursesVideo> list = courseVideoRepo.getVideosByChapterId(video.getChapter().getId());
        if(list.size() == 0){
            video.setNextVideoId(-1);
            video.setHeadVideo(true);
            return courseVideoRepo.save(video);
        }
        else if(video.getNextVideoId() == -1 && list.size() != 0){
            video.setHeadVideo(false);
            video.setNextVideoId(-1);
            Optional<CoursesVideo> currentVideo = courseVideoRepo.findByNextChapterId(video.getChapter().getId(),
                    -1);
            CoursesVideo savedVideo = courseVideoRepo.save(video);
            currentVideo.get().setNextVideoId(savedVideo.getId());
            courseVideoRepo.save(currentVideo.get());
            return savedVideo;
        }
        else {
            Optional<CoursesVideo> nextVideo = courseVideoRepo.findById(video.getNextVideoId());
            if(!nextVideo.isPresent())
                throw new NotFoundException("Can not found next video");
            if(nextVideo.get().isHeadVideo()){
                nextVideo.get().setHeadVideo(false);
                video.setHeadVideo(true);
                courseVideoRepo.save(nextVideo.get());
                return courseVideoRepo.save(video);
            }
            else {
                Optional<CoursesVideo> beforeChap = courseVideoRepo.findByNextChapterId(video.getChapter().getId(),
                        nextVideo.get().getId());
                video.setHeadVideo(false);
                CoursesVideo inserted = courseVideoRepo.save(video);
                beforeChap.get().setNextVideoId(inserted.getId());
                courseVideoRepo.save(beforeChap.get());
                return inserted;
            }
        }
    }

    public CoursesVideo updatePositionVideo(CoursesVideo video, int newNextVideo){
        if(video.isHeadVideo()){
            Optional<CoursesVideo> afterVideo = courseVideoRepo.findById(video.getNextVideoId());
            afterVideo.get().setHeadVideo(true);
            courseVideoRepo.save(afterVideo.get());
        }
        else if(video.getNextVideoId() == -1){
            Optional<CoursesVideo> beforeVideo = courseVideoRepo.findByNextChapterId(video.getChapter().getId(),
                    video.getId());
            beforeVideo.get().setNextVideoId(-1);
            courseVideoRepo.save(beforeVideo.get());
        }
        else if(video.getNextVideoId() != -1 && !video.isHeadVideo()){
            Optional<CoursesVideo> beforeVideo = courseVideoRepo.findByNextChapterId(video.getChapter().getId(),
                    video.getId());
            beforeVideo.get().setNextVideoId(video.getNextVideoId());
            courseVideoRepo.save(beforeVideo.get());
        }
        video.setNextVideoId(newNextVideo);
        return setPositionVideo(video);
    }


}
