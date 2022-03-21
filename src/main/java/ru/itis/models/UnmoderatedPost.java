package ru.itis.models;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "unmoderated_posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UnmoderatedPost extends PostsBase {

    @ElementCollection
    @CollectionTable(name = "unmoderated_post_tags")
    private List<String> tags;

    @Column(name = "book_author")
    private String bookAuthor;

    @Column(name = "is_on_judge")
    private boolean isOnJudge;

    @Column(name = "editor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private DefaultUser editor;
}
