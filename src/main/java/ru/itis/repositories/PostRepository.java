package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.Post;
import ru.itis.models.User;

import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Set<Post> findByAuthor(User user);

    Set<Post> findByAuthorAndStatus(User author, Post.PostStatus status);
}
