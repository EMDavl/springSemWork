package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "book_authors")
@EqualsAndHashCode(exclude = "posts")
public class BookAuthor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String value;

    @OneToMany(mappedBy = "bookAuthor", fetch = FetchType.LAZY)
    private Set<Post> posts;
}
