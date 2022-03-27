package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.BookAuthor;
import ru.itis.models.ModeratedPost;
import ru.itis.models.Tag;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeratedPostDto {

    private Long id;
    private String postText;
    private String bookName;
    private UserAsAuthorDto author;
    private Integer rating;
    private Set<Tag> tags;
    private BookAuthor bookAuthor;

    public static ModeratedPostDto from(ModeratedPost post) {
        return ModeratedPostDto.builder()
                .id(post.getId())
                .postText(post.getPostText())
                .bookName(post.getBookName())
                .author(UserAsAuthorDto.from(post.getAuthor()))
                .rating(post.getLiked().size() - post.getDisliked().size())
                .tags(post.getTags())
                .bookAuthor(post.getBookAuthor())
                .build();
    }

    public static List<ModeratedPostDto> from(List<ModeratedPost> posts) {
        return posts.stream().map(ModeratedPostDto::from).collect(Collectors.toList());
    }

}
