package com.myproject.onlinecourses.service;

import com.myproject.onlinecourses.dto.NoteDTO;
import com.myproject.onlinecourses.dto.ResponseObject;

public interface NoteService {
    ResponseObject getNotesByUsernameAndVideo(String username, Integer courseVideoId);

    ResponseObject getNotesByUsername(String username);

    ResponseObject addNote(NoteDTO dto);

    ResponseObject updateNote(NoteDTO dto);

    ResponseObject deleteNote(Integer id);
}
