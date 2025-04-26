package ru.kpfu.itis.kononenko.shedule.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.mail")
@Data
public class MailProps {
    private String host;
    private String port;
    private String username;
    private String password;
}
