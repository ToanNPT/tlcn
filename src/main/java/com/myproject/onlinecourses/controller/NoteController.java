package com.myproject.onlinecourses.controller;

import com.myproject.onlinecourses.dto.NoteDTO;
import com.myproject.onlinecourses.dto.ResponseObject;
import com.myproject.onlinecourses.service.NoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
public class NoteController {
    @Autowired
    NoteService noteService;

    @PostMapping("note/add")
    public ResponseObject addNote(@RequestBody NoteDTO dto){
        return noteService.addNote(dto);
    }

    @GetMapping("note/{username}/{videoId}")
    public ResponseObject getNotesByUsernameAndVideoId(@PathVariable("username") String username,
                                                       @PathVariable("videoId") Integer id){
        return noteService.getNotesByUsernameAndVideo(username, id);
    }

    @PutMapping("note/update")
    public ResponseObject updateNote(@RequestBody NoteDTO dto){
        return noteService.updateNote(dto);
    }

    @DeleteMapping("note/delete/{id}")
    public ResponseObject deleteNote(@PathVariable("id") Integer id){
        return noteService.deleteNote(id);
    }
}
