package kononenko.ru.kpfu.itis.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import ru.kpfu.itis.kononenko.model.CityWeather;

@Repository
public class CityWeatherRepository {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public CityWeatherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public void create(CityWeather cityWeather) {
        String sql = "INSERT INTO city_weather (city, temperature) VALUES (?, ?)";
        jdbcTemplate.update(sql, cityWeather.getCity(), cityWeather.getTemperature());
    }

    public CityWeather read(Long id) {
        String sql = "SELECT * FROM city_weather WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new Object[]{id}, (rs, rowNum) ->
                new CityWeather(rs.getLong("id"), rs.getString("city"), rs.getDouble("temperature"))
        );
    }

    public void update(CityWeather cityWeather) {
        String sql = "UPDATE city_weather SET city = ?, temperature = ? WHERE id = ?";
        jdbcTemplate.update(sql, cityWeather.getCity(), cityWeather.getTemperature(), cityWeather.getId());
    }

    public void delete(Long id) {
        String sql = "DELETE FROM city_weather WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
