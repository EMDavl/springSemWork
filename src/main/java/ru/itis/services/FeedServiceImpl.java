package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.itis.dto.FeedPage;
import ru.itis.dto.PostDto;
import ru.itis.models.Post;
import ru.itis.repositories.PostRepository;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final PostRepository postRepository;
    @Value("${pagination.default_size}")
    private Integer paginationDefaultSize;

    @Override
    public FeedPage getPosts(Integer page) {
        Page<Post> postPage = postRepository.findAllByStatus(PageRequest.of(page, paginationDefaultSize), Post.PostStatus.APPROVED);
        return FeedPage.builder()
                .posts(PostDto.from(postPage.getContent()))
                .totalPages(postPage.getTotalPages())
                .currentPage(postPage.getNumber())
                .build();
    }
}
