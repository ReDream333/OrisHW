package ru.kpfu.itis.kononenko.shedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.kononenko.shedule.dto.SignUpDto;
import ru.kpfu.itis.kononenko.shedule.enums.TokenStatus;
import ru.kpfu.itis.kononenko.shedule.model.User;
import ru.kpfu.itis.kononenko.shedule.model.VerificationToken;
import ru.kpfu.itis.kononenko.shedule.service.MailService;
import ru.kpfu.itis.kononenko.shedule.service.UserService;
import ru.kpfu.itis.kononenko.shedule.service.VerificationTokenService;


@Controller
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;
    private final VerificationTokenService tokenService;
    private final MailService mailService;

    public AuthController(UserService userService, VerificationTokenService tokenService, MailService mailService) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mailService = mailService;
    }

    @GetMapping("/sign-in")
    public String signIn(Model model) {
        return "auth/sign-in";
    }

    @GetMapping("/sign-up")
    public String signUpGet(Model model) {
        model.addAttribute("signUpForm", new SignUpDto());
        return "auth/sign-up";
    }

    @PostMapping("/sign-up")
    public String signUpPost(
            @ModelAttribute("signUpForm") @Validated SignUpDto signUpDto,
            BindingResult bindingResult,
            Model model) {
        if (bindingResult.hasErrors()) {
            return "auth/sign-up";
        }

        if (!signUpDto.getPassword().equals(signUpDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPasswordNotMatch", "Passwords do not match");
            return "auth/sign-up";
        }

        if (userService.findByUsername(signUpDto.getUsername()).isPresent()) {
            bindingResult.rejectValue("username", "error.usernameExists", "User already exists");
            return "auth/sign-up";
        }

        if (userService.findByEmail(signUpDto.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "error.email", "Email is already in use");
            return "auth/sign-up";
        }

        User newUser = userService.createUser(signUpDto);
        VerificationToken token = tokenService.createToken(newUser);
        mailService.sendVerificationEmail(newUser, token);

        model.addAttribute("email", newUser.getEmail());
        return "auth/confirmation-sent";
    }

    @GetMapping("/confirm")
    public ResponseEntity<String> confirmAccount(@RequestParam("token") String token) {
        TokenStatus status = tokenService.verifyToken(token);
        return switch (status) {
            case VALID -> ResponseEntity.ok("Email подтвержден! Аккаунт активирован.");
            case EXPIRED -> ResponseEntity.badRequest().body("Срок действия токена истек. Запросите новый.");
            case ALREADY_USED -> ResponseEntity.badRequest().body("Токен уже был использован ранее.");
            default -> ResponseEntity.badRequest().body("Неверный токен подтверждения!");
        };
    }
}
