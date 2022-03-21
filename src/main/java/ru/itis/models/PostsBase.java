package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.UUID;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PostsBase {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "post_text", columnDefinition = "text")
    private String postText;

    @Column(name = "book_name")
    private String bookName;

    @ManyToOne(fetch = FetchType.LAZY)
    private DefaultUser author;
}
