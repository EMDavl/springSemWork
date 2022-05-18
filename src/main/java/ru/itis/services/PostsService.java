package ru.itis.services;

import ru.itis.dto.PostCreationDto;
import ru.itis.dto.PostDto;
import ru.itis.dto.PostUpdateDto;
import ru.itis.models.Post;

public interface PostsService {
    PostDto create(PostCreationDto postDto, String userEmail);

    boolean update(PostUpdateDto postDto, String authorEmail);

    boolean delete(Long id, String email);

    boolean isValidInput(Long postId, String email);

    Post findById(Long postId);
}
