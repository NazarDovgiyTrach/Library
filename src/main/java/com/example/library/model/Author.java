package com.example.library.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "authors")
@Data
@EqualsAndHashCode(exclude = {"books"})
@NoArgsConstructor
public class Author {

    @Id
    @SequenceGenerator(name = "author_sequence", sequenceName = "author_id_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "author_sequence")
    @Column(unique = true, nullable = false)
    private Long id;

    @Column
    private String name;

    @Column
    private String surname;

    @OneToMany(mappedBy = "author",cascade = CascadeType.ALL)
    private Set<Book> books;


}
