package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmailNicknameDto {

    private String email;
    private String nickname;

    public static EmailNicknameDto from(User user) {
        return EmailNicknameDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }

}
