package ru.kpfu.itis.kononenko.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import ru.kpfu.itis.kononenko.exception.WeatherException;

public class WeatherUtil {
    private static final String API_KEY = "ec6ae61f58c44f36d3bd7d4f99c9993a";
    private static final String API_URL = "http://api.openweathermap.org/data/2.5/weather?q=%s&appid=%s&units=metric";

    public static Double getTemperature(String city) throws Exception {
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            String url = String.format(API_URL, city, API_KEY);
            HttpGet request = new HttpGet(url);

            try (CloseableHttpResponse response = client.execute(request)) {
                String json = EntityUtils.toString(response.getEntity());
                ObjectMapper mapper = new ObjectMapper();
                JsonNode root = mapper.readTree(json);
                return root.path("main").path("temp").asDouble();
            }
        }catch (Exception e) {
            throw new WeatherException("Проблемы с OpenWeatherApi - %s".formatted(e.getMessage()));
        }
    }
}
