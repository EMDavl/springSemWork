package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Post;
import ru.itis.models.Tag;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDto {

    // TODO мб разделить на 2 класса для запроса и для ответа
    private Long id;
    private String postText;
    private String postTitle;
    private String bookName;
    private String tagsString;
    private Set<String> tags;

    public static PostUpdateDto from(Post post) {

        return PostUpdateDto.builder()
                .id(post.getId())
                .postText(post.getPostText())
                .postTitle(post.getPostTitle())
                .bookName(post.getBookName())
                .tags(post.getTags().stream().map(Tag::getName).collect(Collectors.toSet()))
                .build();

    }
}
