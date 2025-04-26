package ru.kpfu.itis.kononenko.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kpfu.itis.kononenko.config.MailConfig;
import ru.kpfu.itis.kononenko.dto.CreateUserDto;
import ru.kpfu.itis.kononenko.dto.UserDto;
import ru.kpfu.itis.kononenko.entity.User;
import ru.kpfu.itis.kononenko.repository.UserRepository;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final MailConfig mailConfig;

    public UserService(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, JavaMailSender mailSender, MailConfig mailConfig) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.mailSender = mailSender;
        this.mailConfig = mailConfig;
    }

//    public void registerUser(User user) {
//        user.setPassword(passwordEncoder.encode(user.getPassword()));
//        userRepository.save(user);
//    }

    public UserDto create(CreateUserDto dto, String baseUrl) {
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setEmail(dto.getEmail());
        String verification = UUID.randomUUID().toString();
        user.setVerificationCode(verification);

        // send verification code
        sendVerificationEmail(dto, baseUrl, verification);

        return UserDto.fromUser(userRepository.save(user));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream().map(UserDto::fromUser).collect(Collectors.toList());
    }

    private void sendVerificationEmail(CreateUserDto dto, String baseUrl, String verificationCode) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        String content = mailConfig.getContent();

        try {
            mimeMessageHelper.setFrom(mailConfig.getFrom(), mailConfig.getSender());
            mimeMessageHelper.setTo(dto.getEmail());
            mimeMessageHelper.setSubject(mailConfig.getSubject());

            content = content.replace("{name}", dto.getUsername());
            content = content.replace("{url}", baseUrl + "/verification?code=" + verificationCode);

            mimeMessageHelper.setText(content, true);

            mailSender.send(mimeMessage);
        } catch (MessagingException | UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }



    public void registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setEnabled(false);
        user.setVerificationCode(UUID.randomUUID().toString());
        userRepository.save(user);
        sendActivationEmail(user);
    }

    private void sendActivationEmail(User user) {
        String toEmail = user.getEmail();
        String subject = "Подтверждение регистрации";
        String confirmationLink = "http://localhost:8080/verification?code=" + user.getVerificationCode();
        String message = "Здравствуйте, " + user.getName() + "!\n\n" +
                "Спасибо за регистрацию. Для подтверждения аккаунта перейдите по ссылке:\n" +
                confirmationLink + "\n\nЕсли вы не регистрировались на нашем сайте, просто игнорируйте это письмо.";

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setTo(toEmail);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);
        mailSender.send(mailMessage);
    }

    public boolean verifyUser(String code) {
        User user = userRepository.findByVerificationCode(code);
        if (user == null || user.isEnabled()) {
            return false;
        } else {
            user.setEnabled(true);
            user.setVerificationCode(null);
            userRepository.save(user);
            return true;
        }
    }

}
