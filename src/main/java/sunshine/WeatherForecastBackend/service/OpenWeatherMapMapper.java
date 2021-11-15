package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;
import sunshine.WeatherForecastBackend.model.Forecast;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.TimeZone;

@RequiredArgsConstructor
public class OpenWeatherMapMapper {
    public Forecast convertToWeather(OpenWeatherMapDTO openWeatherMapDTO) {
        System.out.println("timeOfObservation: " + openWeatherMapDTO.getDt());
        System.out.println(openWeatherMapDTO.getWeather()[0].getDescription());
        System.out.println((openWeatherMapDTO.getMain()));
        String provider = "OpenWeatherMap";
        LocalDateTime timeOfObservation = LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.parseInt(openWeatherMapDTO.getDt())),
                TimeZone.getDefault().toZoneId());
        System.out.println(timeOfObservation);
        String[] description = Arrays.stream(openWeatherMapDTO.getWeather()).map(OpenWeatherMapDTO.WeatherInMap::getDescription).toArray(String[]::new);
        String cityName = openWeatherMapDTO.getName();

        double pressure = Double.parseDouble(openWeatherMapDTO.getMain().getPressure());
//        double seaLevelPressure = Double.parseDouble(openWeatherMapDTO.getMain().getSea_level());
        double windSpeed = Double.parseDouble(openWeatherMapDTO.getWind().getSpeed());
        int windDirection = Integer.parseInt(openWeatherMapDTO.getWind().getDeg());
        double temperature = Double.parseDouble(openWeatherMapDTO.getMain().getTemp());
        double apparentTemperature = Double.parseDouble(openWeatherMapDTO.getMain().getFeels_like());
        int clouds = Integer.parseInt(openWeatherMapDTO.getClouds().getAll());
        int humidity = Integer.parseInt(openWeatherMapDTO.getMain().getHumidity());
        System.out.println(openWeatherMapDTO.getRain());
        System.out.println(openWeatherMapDTO.getSnow());
        double precipitation;
        OpenWeatherMapDTO.Rain rain;
        if ((rain = openWeatherMapDTO.getRain()) != null) {
            precipitation = Double.parseDouble(rain.getRain1h());
        } else {
            precipitation = 0;
        }
        OpenWeatherMapDTO.Snow snow;
        double snowAmount;
        if ((snow = openWeatherMapDTO.getSnow()) != null) {
            snowAmount = Double.parseDouble(snow.getSnow1h());
        } else {
            snowAmount = 0;
        }
        return new Forecast(provider, timeOfObservation, description, cityName, pressure, /*seaLevelPressure,*/ windSpeed, windDirection, temperature, apparentTemperature, clouds, humidity, precipitation, snowAmount);
    }
}
