package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long> {

}
