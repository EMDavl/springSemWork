package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {

    private String postText;
    private String bookName;
    private String bookAuthor;
    private Set<String> tags;

}
