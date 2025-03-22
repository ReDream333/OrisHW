package kononenko.ru.kpfu.itis.controller;

import org.springframework.web.bind.annotation.*;
import ru.kpfu.itis.kononenko.model.CityWeather;
import ru.kpfu.itis.kononenko.repository.CityWeatherRepository;

@RestController
@RequestMapping("/weather")
public class WeatherController {

    private final CityWeatherRepository repository;


    public WeatherController(CityWeatherRepository repository) {
        this.repository = repository;

    }

    @PostMapping("/city")
    public CityWeather createCityWeather(@RequestBody CityWeather cityWeather) {
        repository.create(cityWeather);
        return cityWeather;
    }

    @GetMapping("/city/{id}")
    public CityWeather getCityWeather(@PathVariable("id") Long id) {
        return repository.read(id);
    }

    @PutMapping("/city")
    public CityWeather updateCityWeather(@RequestBody CityWeather cityWeather) {
        repository.update(cityWeather);
        return cityWeather;
    }

    @DeleteMapping("/city/{id}")
    public CityWeather deleteCityWeather(@PathVariable("id") Long id) {
        CityWeather cityWeather = repository.read(id);
        repository.delete(id);
        return cityWeather;
    }
}
