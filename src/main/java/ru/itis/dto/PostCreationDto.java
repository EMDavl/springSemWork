package ru.itis.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostCreationDto {

    private String postText;
    private String postTitle;
    private String bookName;
    private String bookAuthor;
    private String tagsString;
    private List<String> tags;

}
