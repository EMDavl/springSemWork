package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.Tag;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TagDto {
    private Long id;
    private String value;

    public static TagDto from(Tag tag) {
        return TagDto.builder()
                .id(tag.getId())
                .value(tag.getName())
                .build();
    }

    public static Set<TagDto> from(Collection<Tag> tags) {
        return tags.stream().map(TagDto::from).collect(Collectors.toSet());
    }
}
