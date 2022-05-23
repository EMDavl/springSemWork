package ru.itis.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.itis.models.Post;
import ru.itis.models.User;

import java.util.List;
import java.util.Set;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    Page<Post> findAllByStatus(Pageable pageable, Post.PostStatus status);

    Set<Post> findByAuthorAndStatus(User author, Post.PostStatus status);

    @Query("SELECT p FROM Post p WHERE p IN (SELECT t.posts FROM Tag t WHERE t.id=:id)")
    List<Post> findAllByTag(@Param("id") Long id);

    @Query("SELECT p FROM Post p WHERE p IN (SELECT t.posts FROM BookAuthor t WHERE t.id=:id)")
    List<Post> findAllByBookAuthor(Long id);

    List<Post> findAllByBookName(String name);
}
