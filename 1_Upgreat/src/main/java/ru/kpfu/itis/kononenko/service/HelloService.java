package ru.kpfu.itis.kononenko.service;

import org.springframework.stereotype.Component;

@Component
public class HelloService
{
    public String sayHello(String name) {
        return "Hello %s".formatted(name);
    }
}
