package ru.itis.services;

public interface MailService {

    void sendSignUpConfirmationMail(String to, String subj, String text);

}
