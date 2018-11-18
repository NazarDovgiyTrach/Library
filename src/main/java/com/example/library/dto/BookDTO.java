package com.example.library.dto;

import com.example.library.model.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@NoArgsConstructor
public class BookDTO {

    @NotEmpty(message = "Title must contain at least 2 characters!")
    private String title;
    private Integer year;
    private Genre genre;


}
