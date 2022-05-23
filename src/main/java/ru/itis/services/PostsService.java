package ru.itis.services;

import org.springframework.http.ResponseEntity;
import ru.itis.dto.PostCreationDto;
import ru.itis.dto.PostDto;
import ru.itis.dto.PostUpdateDto;
import ru.itis.dto.RatingChanges;
import ru.itis.models.Post;

import java.util.List;

public interface PostsService {
    PostDto create(PostCreationDto postDto, String userEmail);

    boolean update(PostUpdateDto postDto, String authorEmail);

    boolean delete(Long id, String email);

    boolean isValidInput(Long postId, String email);

    Post findById(Long postId);

    ResponseEntity<RatingChanges> like(Long postId, String email);

    ResponseEntity<RatingChanges> dislike(Long postId, String email);

    List<PostDto> getByTag(Long id);

    List<PostDto> getByBookAuthor(Long id);

    List<PostDto> getByBookName(String name);
}
