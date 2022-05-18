package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.models.BookAuthor;
import ru.itis.repositories.BookAuthorRepository;

@Service
@RequiredArgsConstructor
public class BookAuthorServiceImpl implements BookAuthorService {

    private final BookAuthorRepository repository;

    @Override
    public BookAuthor findOrCreateByName(String value) {

        return repository.findByValueIgnoreCase(value)
                .orElseGet(() -> {
                    BookAuthor author = BookAuthor.builder()
                            .value(value)
                            .build();
                    repository.save(author);
                    return author;
                });

    }
}
