package ru.itis.exceptions;

public class PostNotFound extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Wrong id provided";

    public PostNotFound() {
        super(DEFAULT_MESSAGE);
    }
}
