package ru.kpfu.itis.kononenko.shedule.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.kononenko.shedule.model.User;
import ru.kpfu.itis.kononenko.shedule.model.VerificationToken;
import ru.kpfu.itis.kononenko.shedule.properties.MailProps;
import ru.kpfu.itis.kononenko.shedule.repository.VerificationTokenRepository;

@Slf4j
@RequiredArgsConstructor
@Service
public class MailService {
    private final JavaMailSender mailSender;

    private final MailProps mailProps;

    public void sendVerificationEmail(User user, VerificationToken token) {
        String toEmail = user.getEmail();
        String subject = "Подтверждение регистрации";
        String confirmUrl = "http://localhost:8080/auth/confirm?token=" + token.getToken();
        String message = "Здравствуйте, " +
                "\n\nДля подтверждения учетной записи перейдите по ссылке: "
                + confirmUrl +
                "\n\nСсылка действительна 15 минут.";

        sendSimpleEmail(toEmail, subject, message);
    }

    public void sendSimpleEmail(String toEmail, String subject, String message) {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(mailProps.getUsername());
            msg.setTo(toEmail);
            msg.setSubject(subject);
            msg.setText(message);

            mailSender.send(msg);
            log.info("Email sent to {}", toEmail);
        } catch (MailException e) {
            log.error("Can't send email to {}", toEmail, e);
        }
    }
}
