package sunshine.WeatherForecastBackend.service;

import sunshine.WeatherForecastBackend.model.Forecast;

import java.util.List;

public interface ForecastImporter {
    List<Forecast> importForecasts(String city, String units);
}
