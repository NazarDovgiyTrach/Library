package com.example.library.dto;

import com.example.library.model.Genre;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BookResponseDTO {
    private Long id;
    private String title;
    private AuthorBookResponseDTO author;
    private Genre genre;
    private Integer year;
    private String filename;
    private String imagePath;
}
