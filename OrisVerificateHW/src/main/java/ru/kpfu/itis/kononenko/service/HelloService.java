package ru.kpfu.itis.kononenko.service;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

@Component
public class HelloService
{
    public String sayHello(String name) {
        return "Hello %s".formatted(name);
    }
}
