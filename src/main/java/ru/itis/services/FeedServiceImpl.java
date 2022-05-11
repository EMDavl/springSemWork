package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.itis.models.ModeratedPost;
import ru.itis.repositories.ModeratedPostRepository;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final ModeratedPostRepository postRepository;

    @Override
    public Page<ModeratedPost> getPosts(Pageable pageable) {
        return postRepository.findAll(pageable);
    }
}
