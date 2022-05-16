package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Post;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostsDto {

    private String postText;
    private String postTitle;
    private String bookName;
    private BookAuthorDto bookAuthor;
    private Set<TagDto> tags;
    private long likes;
    private long dislikes;

    public static PostsDto from(Post post) {
        return PostsDto.builder()
                .postText(post.getPostText())
                .postTitle(post.getPostTitle())
                .bookName(post.getBookName())
                .bookAuthor(BookAuthorDto.from(post.getBookAuthor()))
                .tags(TagDto.from(post.getTags()))
                .likes(post.getLiked().size())
                .dislikes(post.getDisliked().size())
                .build();
    }

    public static Set<PostsDto> from(Collection<Post> posts) {
        return posts.stream().map(PostsDto::from).collect(Collectors.toSet());
    }
}
