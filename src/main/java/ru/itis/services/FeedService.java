package ru.itis.services;

import ru.itis.dto.FeedPage;

public interface FeedService {

    FeedPage getPosts(Integer pageable);

}
