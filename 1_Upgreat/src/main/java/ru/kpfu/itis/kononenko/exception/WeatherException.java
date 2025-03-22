package ru.kpfu.itis.kononenko.exception;

public class WeatherException extends Exception{
    public WeatherException(String message) {
        System.out.println(message);
    }
}
