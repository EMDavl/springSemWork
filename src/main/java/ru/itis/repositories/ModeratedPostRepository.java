package ru.itis.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.ModeratedPost;

@Repository
public interface ModeratedPostRepository extends JpaRepository<ModeratedPost, Long> {

    @Override
    Page<ModeratedPost> findAll(Pageable pageable);

}
