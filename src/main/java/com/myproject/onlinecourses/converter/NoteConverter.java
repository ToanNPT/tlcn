package com.myproject.onlinecourses.converter;

import com.myproject.onlinecourses.dto.NoteDTO;
import com.myproject.onlinecourses.entity.Note;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface NoteConverter {

    NoteDTO entityToDTO(Note note);

}
