package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.models.BookAuthor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookAuthorDto {
    private Long id;
    private String value;

    public static BookAuthorDto from(BookAuthor author) {
        return BookAuthorDto.builder()
                .id(author.getId())
                .value(author.getValue())
                .build();
    }
}
