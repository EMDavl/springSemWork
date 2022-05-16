package ru.itis.services;

import ru.itis.models.BookAuthor;

public interface BookAuthorService {

    BookAuthor findOrCreateByName(String value);
}
