package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.User;

import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModeratorDto {

    private Long id;
    private String email;

    public static List<ModeratorDto> from(List<User> moders) {
        return moders.stream().map(ModeratorDto::from).collect(Collectors.toList());
    }

    public static ModeratorDto from(User moder) {
        return ModeratorDto.builder()
                .email(moder.getEmail())
                .id(moder.getId())
                .build();
    }
}
