package ru.kpfu.itis.kononenko.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kpfu.itis.kononenko.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class VerificationController {

    private final UserService userService;

    public VerificationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/verification")
    public String verifyAccount(@RequestParam("code") String code) {
        boolean verified = userService.verifyUser(code);
        if (verified) {
            return "verification_success";
        } else {
            return "verification_fail";
        }
    }
}
