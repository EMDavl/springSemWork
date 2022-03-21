package ru.itis.models;

import lombok.*;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "unmoderated_posts")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UnmoderatedPost extends PostsBase {

    @ElementCollection
    @CollectionTable(name = "unmoderated_post_tags")
    private List<String> tags;
}
