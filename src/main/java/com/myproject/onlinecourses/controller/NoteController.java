package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.NoteDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.exception.ForbiddenException;
import com.myproject.onlinecourses.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/v1/")
public class NoteController {
    @Autowired
    NoteService noteService;

    @GetMapping("notes/{id}")
    public ResponseObject getNoteById(@PathVariable("id") Integer id, Principal principal){
        if (principal == null)
            throw new ForbiddenException();

        return noteService.getNoteById(id, principal.getName());
    }

    @PostMapping("notes")
    public ResponseObject addNote(@RequestBody NoteDTO dto, Principal principal){
        if(principal == null)
            throw new ForbiddenException();
        dto.setUsername(principal.getName());
        return noteService.addNote(dto);
    }

    @GetMapping("notes/video/{videoId}")
    public ResponseObject getNotesByUsernameAndVideoId(Principal principal,
                                                       @PathVariable("videoId") Integer id){
        if(principal == null)
            throw new ForbiddenException();

        return noteService.getNotesByUsernameAndVideo(principal.getName(), id);
    }

    @PutMapping("notes/{id}")
    public ResponseObject updateNote(@PathVariable("id") Integer id, @RequestBody NoteDTO dto, Principal principal){
        if(principal == null)
            throw new ForbiddenException();
        dto.setId(id);
        dto.setUsername(principal.getName());
        return noteService.updateNote(dto);
    }

    @DeleteMapping("notes/{id}")
    public ResponseObject deleteNote(@PathVariable("id") Integer id, Principal principal){
        if(principal == null)
            throw new ForbiddenException();

        return noteService.deleteNote(id, principal.getName());
    }
}
