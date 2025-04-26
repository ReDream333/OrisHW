package ru.kpfu.itis.kononenko.shedule.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "currency.api")
@Data
public class CurrencyApiProps {
    private String url;
    private String key;
}