package ru.kpfu.itis.kononenko.shedule.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ProfileController {

    @GetMapping("/profile")
    public ResponseEntity<String> getProfile(Authentication authentication) {

        return ResponseEntity.ok(authentication.getPrincipal().toString());

    }
}
