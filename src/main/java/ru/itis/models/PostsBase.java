package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;

@MappedSuperclass
@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public abstract class PostsBase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "post_text", columnDefinition = "text")
    protected String postText;

    @Column(name = "book_name")
    protected String bookName;

    @ManyToOne(fetch = FetchType.LAZY)
    protected User author;
}
