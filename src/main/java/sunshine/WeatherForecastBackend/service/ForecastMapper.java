package sunshine.WeatherForecastBackend.service;

import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherDto;

import java.util.List;

public interface ForecastMapper {
    public List<Forecast> convertToForecasts(WeatherDto weatherDto);
}
