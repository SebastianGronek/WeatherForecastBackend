package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;
import sunshine.WeatherForecastBackend.model.WeatherDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.TimeZone;

@RequiredArgsConstructor
public class OpenWeatherMapMapper implements ForecastMapper {
    public List<Forecast> convertToForecasts(WeatherDto openWeatherMapDTO) {
        return List.of(convertSingleResponseToForecast((OpenWeatherMapDTO) openWeatherMapDTO));
    }

    private static Forecast convertSingleResponseToForecast(OpenWeatherMapDTO openWeatherMapDTO) {
        String provider = "OpenWeatherMap";
        LocalDateTime timeOfObservation = LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.parseInt(openWeatherMapDTO.getDt())),
                TimeZone.getDefault().toZoneId());
        String[] description = Arrays.stream(openWeatherMapDTO.getWeather()).map(OpenWeatherMapDTO.WeatherInMap::getDescription).toArray(String[]::new);
        String cityName = openWeatherMapDTO.getName();
        double pressure = Double.parseDouble(openWeatherMapDTO.getMain().getPressure());
        double windSpeed = Double.parseDouble(openWeatherMapDTO.getWind().getSpeed());
        int windDirection = Integer.parseInt(openWeatherMapDTO.getWind().getDeg());
        double temperature = Double.parseDouble(openWeatherMapDTO.getMain().getTemp());
        double apparentTemperature = Double.parseDouble(openWeatherMapDTO.getMain().getFeels_like());
        int clouds = Integer.parseInt(openWeatherMapDTO.getClouds().getAll());
        int humidity = Integer.parseInt(openWeatherMapDTO.getMain().getHumidity());
        double precipitation = rainAmount(openWeatherMapDTO);
        double snowAmount = snowAmount(openWeatherMapDTO);
        return new Forecast(provider, timeOfObservation, description, cityName, pressure, windSpeed, windDirection, temperature, apparentTemperature, clouds, humidity, precipitation, snowAmount);
    }

    private static double rainAmount(OpenWeatherMapDTO openWeatherMapDTO) {
        OpenWeatherMapDTO.Rain rain;
        if ((rain = openWeatherMapDTO.getRain()) != null) {
            return Double.parseDouble(rain.getRain1h());
        } else {
            return 0;
        }
    }

    private static double snowAmount(OpenWeatherMapDTO openWeatherMapDTO) {
        OpenWeatherMapDTO.Snow snow;
        if ((snow = openWeatherMapDTO.getSnow()) != null) {
            return Double.parseDouble(snow.getSnow1h());
        } else {
            return 0;
        }
    }
}
