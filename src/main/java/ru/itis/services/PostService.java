package ru.itis.services;

import org.springframework.data.domain.Page;
import ru.itis.dto.PostRequestDto;
import ru.itis.models.ModeratedPost;
import ru.itis.models.User;

import java.util.List;
import java.util.Set;

public interface PostService {

    ModeratedPost save(PostRequestDto post);

    Page<ModeratedPost> getWithPagination(Integer page, Integer size);

    ModeratedPost delete(Long id);

    ModeratedPost update(PostRequestDto post);

    void changeRating(Set<User> liked, Set<User> disliked);

    List<ModeratedPost> findAll();

    boolean postExist(Long postId);
}
