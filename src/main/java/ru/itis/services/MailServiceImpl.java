package ru.itis.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MailServiceImpl implements MailService {

    private final JavaMailSender mailSender;

    @Value("${mail.user}")
    private String mailFrom;
    @Value("${mail.post_link}")
    private String postLink;

    @Override
    public void sendSignUpConfirmationMail(String to, String subj, String text) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailFrom);
        message.setTo(to);
        message.setSubject(subj);
        message.setText(text);

        mailSender.send(message);
    }

    @Override
    public void sendPostWasDeclined(String email, Long id) {
        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(mailFrom);
        message.setTo(email);
        message.setSubject("Your post was declined");
        message.setText("Your post " + postLink + id + " was declined");

        mailSender.send(message);
    }
}
