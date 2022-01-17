package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;

@RequiredArgsConstructor
public class OpenWeatherMapMapper {
    public List<Forecast> convertToForecasts(OpenWeatherMapDTO openWeatherMapDTO) {
        return List.of(convertSingleResponseToForecast(openWeatherMapDTO));
    }

    private static Forecast convertSingleResponseToForecast(OpenWeatherMapDTO openWeatherMapDTO) {
        return Forecast.builder()
                .provider("OpenWeatherMap")
                .lastObservationTime(getLastObservationTime(openWeatherMapDTO))
                .description(gettingDescriptionFromOpenWeatherMapDTO(openWeatherMapDTO))
                .cityName(openWeatherMapDTO.getName())
                .pressure(Double.parseDouble(openWeatherMapDTO.getMain().getPressure()))
                .windSpeed(Double.parseDouble(openWeatherMapDTO.getWind().getSpeed()))
                .windDirection(Integer.parseInt(openWeatherMapDTO.getWind().getDeg()))
                .temperature(Double.parseDouble(openWeatherMapDTO.getMain().getTemp()))
                .apparentTemperature(Double.parseDouble(openWeatherMapDTO.getMain().getFeels_like()))
                .clouds(Integer.parseInt(openWeatherMapDTO.getClouds().getAll()))
                .humidity(Integer.parseInt(openWeatherMapDTO.getMain().getHumidity()))
                .precipitation(Optional.of(Double.parseDouble(openWeatherMapDTO.getRain().getRain1h())).orElse(0.0))
                .snow(Optional.of(Double.parseDouble(openWeatherMapDTO.getSnow().getSnow1h())).orElse(0.0))
                .build();
    }

    private static String[] gettingDescriptionFromOpenWeatherMapDTO(OpenWeatherMapDTO openWeatherMapDTO) {
        return Arrays.stream(openWeatherMapDTO.getWeather()).map(OpenWeatherMapDTO.WeatherInMap::getDescription).toArray(String[]::new);
    }

    private static LocalDateTime getLastObservationTime(OpenWeatherMapDTO openWeatherMapDTO) {
        return LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.parseInt(openWeatherMapDTO.getDt())),
                TimeZone.getDefault().toZoneId());
    }
}
