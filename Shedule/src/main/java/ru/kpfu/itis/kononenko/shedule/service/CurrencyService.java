package ru.kpfu.itis.kononenko.shedule.service;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import ru.kpfu.itis.kononenko.shedule.properties.CurrencyApiProps;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


@Slf4j
@Service
public class CurrencyService {

    private final CurrencyApiProps props;
    private final WebClient client;

    public CurrencyService(CurrencyApiProps props, WebClient.Builder builder) {
        this.props = props;
        this.client = builder.baseUrl(props.getUrl()).build();
    }

    public Map<String, BigDecimal> getUsdRub() {

        JsonNode json = client.get()
                .uri(uri -> uri.path("/latest")
                        .queryParam("access_key", props.getKey())
                        .queryParam("symbols", "USD,RUB")
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        assert json != null;
        if (!json.path("success").asBoolean(false)) {
            throw new IllegalStateException("Fixer API error: " + json);
        }

        JsonNode rates = json.path("rates");
        BigDecimal eurUsd = rates.path("USD").decimalValue();
        BigDecimal eurRub = rates.path("RUB").decimalValue();

        BigDecimal usdRub = eurRub.divide(eurUsd, 6, RoundingMode.HALF_UP);
        return Map.of("USD/RUB", usdRub);
    }


    public Map<String, BigDecimal> getPopularToRub() {

        List<String> popular = List.of("USD", "EUR", "GBP", "JPY",
                "CNY", "CHF", "KZT", "TRY");

        String symbols = String.join(",", popular) + ",RUB";

        JsonNode json = client.get()
                .uri(uri -> uri.path("/latest")
                        .queryParam("access_key", props.getKey())
                        .queryParam("symbols", symbols)
                        .build())
                .retrieve()
                .bodyToMono(JsonNode.class)
                .block();

        assert json != null;
        if (!json.path("success").asBoolean(false))
            throw new IllegalStateException("Fixer error: " + json);

        JsonNode rates = json.get("rates");
        BigDecimal eurRub = rates.path("RUB").decimalValue();

        Map<String, BigDecimal> map = new LinkedHashMap<>();
        for (String cur : popular) {
            BigDecimal eurCur = rates.path(cur).decimalValue();
            BigDecimal curRub = eurRub.divide(eurCur, 6, RoundingMode.HALF_UP);
            map.put(cur, curRub);
        }
        return map;
    }
}
