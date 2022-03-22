package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.ModeratedPost;

@Repository
public interface PostsRepository extends JpaRepository<ModeratedPost, Long> {

}
