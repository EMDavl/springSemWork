package ru.itis.services;

import ru.itis.dto.PostCreationDto;
import ru.itis.dto.PostDto;

public interface PostsService {
    PostDto create(PostCreationDto postDto, String userEmail);

    PostDto update(PostDto postDto);

    PostDto delete(Long id);
}
