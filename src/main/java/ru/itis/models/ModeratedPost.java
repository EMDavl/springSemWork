package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "posts")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModeratedPost extends PostsBase {

    @ManyToMany()
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookAuthor bookAuthor;

    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
    private Set<User> liked;

    @ManyToMany(mappedBy = "dislikedPosts", fetch = FetchType.LAZY)
    private Set<User> disliked;
}
