package ru.itis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itis.models.BookAuthor;

import java.util.Optional;

@Repository
public interface BookAuthorRepository extends JpaRepository<BookAuthor, Long> {
    Optional<BookAuthor> findByValueIgnoreCase(String value);
}
