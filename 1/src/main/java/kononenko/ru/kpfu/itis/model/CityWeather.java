package kononenko.ru.kpfu.itis.model;

public class CityWeather {
    private Long id;
    private String city;
    private Double temperature;

    public CityWeather() {}

    public CityWeather(Long id, String city, Double temperature) {
        this.id = id;
        this.city = city;
        this.temperature = temperature;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Double getTemperature() {
        return temperature;
    }

    public void setTemperature(Double temperature) {
        this.temperature = temperature;
    }
}
