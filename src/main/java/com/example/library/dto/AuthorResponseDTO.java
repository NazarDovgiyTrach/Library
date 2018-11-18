package com.example.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class AuthorResponseDTO {
    private Long id;
    private String name;
    private String surname;
    private Set<BookAuthorResponseDTO> books;

}
