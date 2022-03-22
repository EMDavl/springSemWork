package ru.itis.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserAsAuthorDto {

    private Long id;
    private String nickname;


    public static UserAsAuthorDto from(User author) {
        return UserAsAuthorDto.builder()
                .id(author.getId())
                .nickname(author.getNickname())
                .build();
    }
}
