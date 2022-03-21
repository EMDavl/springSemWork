package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;
import java.util.UUID;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
public abstract class PostsBase {

    @Id
    @Column(name = "id")
    protected UUID id;

    @Column(name = "post_text", columnDefinition = "text")
    protected String postText;

    @Column(name = "book_name")
    protected String bookName;

    @ManyToOne(fetch = FetchType.LAZY)
    protected DefaultUser author;
}
