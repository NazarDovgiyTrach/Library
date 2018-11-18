package com.example.library.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
@Data
@NoArgsConstructor
public class AuthorDTO {

    @NotEmpty(message = "Name must contain at least 2 characters!")
    @Pattern(regexp = "^[A-Z][a-z]{1,30}([\\-][A-Z][a-z]{1,30}|)$", message = "Name can contain only latin letters and - sign!")
    private String name;

    @NotEmpty(message = "Surname must contain at least 2 characters!")
    @Pattern(regexp = "^[A-Z][a-z]{1,30}([\\-][A-Z][a-z]{1,30}|)$", message = "Surname can contain only latin letters and - sign!")
    private String surname;
}
