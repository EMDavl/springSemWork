package ru.itis.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "posts")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "post_title")
    private String postTitle;

    @Column(name = "post_text", columnDefinition = "text")
    protected String postText;

    @Column(name = "book_name")
    protected String bookName;

    @ManyToOne(fetch = FetchType.LAZY)
    protected User author;

    @ManyToMany()
    @JoinTable(
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;

    @ManyToOne(fetch = FetchType.LAZY)
    private BookAuthor bookAuthor;

    private PostStatus status;

    @ManyToMany(mappedBy = "likedPosts", fetch = FetchType.LAZY)
    private Set<User> liked;

    @ManyToMany(mappedBy = "dislikedPosts", fetch = FetchType.LAZY)
    private Set<User> disliked;

    public enum PostStatus {
        APPROVED, ON_MODERATION, DELETED
    }
}
