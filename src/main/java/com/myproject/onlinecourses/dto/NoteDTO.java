package com.myproject.onlinecourses.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NoteDTO {
    private int id;
    private String username;
    private int videoId;
    private String content;
    private String atTime;
}
