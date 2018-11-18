package com.example.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
class BookAuthorResponseDTO {
    private Long id;
    private String title;
}