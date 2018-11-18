package com.example.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AuthorBookResponseDTO {
    private Long id;
    private String name;
    private String surname;
}
