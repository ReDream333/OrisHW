package ru.kpfu.itis.kononenko.shedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import ru.kpfu.itis.kononenko.shedule.properties.CurrencyApiProps;
import ru.kpfu.itis.kononenko.shedule.properties.MailProps;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties({CurrencyApiProps.class, MailProps.class})
public class SheduleApplication {

    public static void main(String[] args) {
        SpringApplication.run(SheduleApplication.class, args);

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
