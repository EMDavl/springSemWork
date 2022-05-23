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
public class FeedPage {

    private List<PostDto> posts;
    private int totalPages;
    private int currentPage;

    public boolean hasPrevious() {
        return currentPage > 0;
    }

    public boolean hasNext() {
        return currentPage < totalPages - 1;
    }
}
