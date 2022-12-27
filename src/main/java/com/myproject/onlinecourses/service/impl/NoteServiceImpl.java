package com.myproject.onlinecourses.service.impl;

import com.myproject.onlinecourses.converter.NoteConverter;
import com.myproject.onlinecourses.dto.NoteDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.entity.Account;
import com.myproject.onlinecourses.entity.CoursePaid;
import com.myproject.onlinecourses.entity.CoursesVideo;
import com.myproject.onlinecourses.entity.Note;
import com.myproject.onlinecourses.exception.NotFoundException;
import com.myproject.onlinecourses.repository.*;
import com.myproject.onlinecourses.service.CoursePaidService;
import com.myproject.onlinecourses.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NoteServiceImpl implements NoteService {
    @Autowired
    NoteRepository noteRepo;

    @Autowired
    AccountRepository accountRepo;

    @Autowired
    CourseVideoRepository courseVideoRepo;

    @Autowired
    CoursesRepository coursesRepo;

    @Autowired
    CoursePaidRepository coursePaidRepository;

    @Autowired
    NoteConverter converter;

    @Override
    public ResponseObject getNoteById(Integer id){
        Optional<Note> note = noteRepo.findById(id);
        if(!note.isPresent())
            throw new NotFoundException("Can not found note with id "+ id);
        return new ResponseObject(converter.entityToDTO(note.get()));
    }
    @Override
    public ResponseObject getNotesByUsernameAndVideo(String username, Integer courseVideoId){
        List<Note> notes = noteRepo.getNotesByUsernameAnAndVideoId(username, courseVideoId);
        return new ResponseObject(notes);
    }

    @Override
    public ResponseObject getNotesByUsername(String username){
        List<Note> notes = noteRepo.getNotesByUsername(username);
        return new ResponseObject(notes);
    }

    @Override
    public ResponseObject addNote(NoteDTO dto){
        Optional<Account> account = accountRepo.findById(dto.getUsername());
        Optional<CoursesVideo> video = courseVideoRepo.findById(dto.getVideoId());
        if(!account.isPresent() || !video.isPresent())
            throw new NotFoundException("Something is wrong when try adding");

        Optional<CoursePaid> check = coursePaidRepository.isPaid(video.get().getCourse().getId(),
                dto.getUsername());
        if(!check.isPresent())
            throw new NotFoundException("You did not buy this course");

        Note note = new Note();
        note.setUsername(dto.getUsername());
        note.setVideoId(dto.getVideoId());
        note.setContent(dto.getContent());
        note.setAtTime(dto.getAtTime());

        Note res = noteRepo.save(note);
        return new ResponseObject(res);
    }

    @Override
    public ResponseObject updateNote(NoteDTO dto){
        Optional<Note> note = noteRepo.findById(dto.getId());
        if(!note.isPresent()) throw new NotFoundException("Can not found note");
        Optional<Account> account = accountRepo.findById(dto.getUsername());
        Optional<CoursesVideo> video = courseVideoRepo.findById(dto.getVideoId());
        if(!account.isPresent() || !video.isPresent())
            throw new NotFoundException("Something is wrong when try adding");

        String check = coursesRepo.checkPurchaseCourse(dto.getUsername(), video.get().getCourse().getId());
        if(check == null)
            throw new NotFoundException("You did not buy this course");

        note.get().setAtTime(dto.getAtTime());
        note.get().setContent(dto.getContent());

        return new ResponseObject(noteRepo.save(note.get()));

    }

    @Override
    public ResponseObject deleteNote(Integer id){
        Optional<Note> note = noteRepo.findById(id);
        if(!note.isPresent()) throw new NotFoundException("Can not found note");
        noteRepo.delete(note.get());
        return new ResponseObject("", "200", "delete successful", null);
    }
}

