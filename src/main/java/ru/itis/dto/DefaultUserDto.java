package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Post;
import ru.itis.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DefaultUserDto {

    private Long id;
    private String nickname;
    private String email;
    private long moderatedPostsAmount;
    private long unmoderatedPostsAmount;
    private boolean isPublicAccount;
    private long subscribers;
    private long subscriptions;

    public static DefaultUserDto from(User user) {
        return DefaultUserDto.builder()
                .id(user.getId())
                .nickname(user.getNickname())
                .email(user.getEmail())
                .moderatedPostsAmount(user.getPosts()
                        .stream()
                        .filter(p -> p.getStatus().equals(Post.PostStatus.APPROVED))
                        .count())
                .unmoderatedPostsAmount(user.getPosts()
                        .stream()
                        .filter(p -> p.getStatus().equals(Post.PostStatus.ON_MODERATION))
                        .count())
                .isPublicAccount(user.isPublicAccount())
                .subscribers(user.getSubscribers().size())
                .subscriptions(user.getSubscriptions().size())
                .build();
    }
}
