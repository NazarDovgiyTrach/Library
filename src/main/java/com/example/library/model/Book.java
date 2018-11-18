package com.example.library.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "books")
@Data
@NoArgsConstructor
public class Book  {

    @Id
    @SequenceGenerator(name = "book_sequence", sequenceName = "book_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_sequence")
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private String title;
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "author_id",nullable = false)
    private Author author;

    @Enumerated(value = EnumType.STRING)
    @Column
    private Genre genre;

    @Column
    private Integer year;

    @Column
    private String filename;

    @Column(name = "image_path")
    private String imagePath;


}
