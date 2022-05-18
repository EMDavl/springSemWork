package ru.itis.exceptions;

public class AccountNotConfirmedException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Account not confirmed";

    public AccountNotConfirmedException() {
        super(DEFAULT_MESSAGE);
    }
}
