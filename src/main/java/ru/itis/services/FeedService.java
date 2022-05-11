package ru.itis.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.models.ModeratedPost;

public interface FeedService {

    Page<ModeratedPost> getPosts(Pageable pageable);

}
