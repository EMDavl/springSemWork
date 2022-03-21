package ru.itis.models;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posts")
public class ModeratedPost extends PostsBase {

    @ManyToMany()
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookAuthor bookAuthor;

}