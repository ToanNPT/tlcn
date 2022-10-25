package com.myproject.onlinecourses.repository;

import com.myproject.onlinecourses.entity.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NoteRepository extends JpaRepository<Note, Integer> {

    @Query("select n " +
            "from Note as n " +
            "where n.username = :username and n.videoId = :videoId")
    List<Note> getNotesByUsernameAnAndVideoId(String username, Integer videoId);

    @Query("select n " +
            "from Note as n " +
            "where n.username = :username")
    List<Note> getNotesByUsername(String username);
}
