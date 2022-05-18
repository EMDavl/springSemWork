package ru.itis.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import ru.itis.models.Post;

public interface FeedService {

    Page<Post> getPosts(Pageable pageable);

}
