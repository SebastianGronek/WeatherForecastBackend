package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;
import sunshine.WeatherForecastBackend.model.Weather;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@RequiredArgsConstructor
public class OpenWeatherMapMapper {
    public Weather convertToWeather(OpenWeatherMapDTO openWeatherMapDTO) {
        System.out.println("timeOfObservation: "+openWeatherMapDTO.getDt());
        System.out.println(openWeatherMapDTO.getWeather()[0].getDescription());
        System.out.println((openWeatherMapDTO.getMain()));
        long time = Integer.parseInt(openWeatherMapDTO.getDt()) * 1000L;
        LocalDateTime timeOfObservation = LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                TimeZone.getDefault().toZoneId());
        System.out.println(timeOfObservation);
        String description = openWeatherMapDTO.getWeather()[0].getDescription();
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
        if ((rain=openWeatherMapDTO.getRain()) != null) {
            precipitation = Double.parseDouble(rain.getRain1h());
        } else {
            precipitation = 0;
        }
        OpenWeatherMapDTO.Snow snow;
        double snowAmount;
        if ((snow=openWeatherMapDTO.getSnow()) != null) {
            snowAmount = Double.parseDouble(snow.getSnow1h());
        } else {
            snowAmount = 0;
            }
        return new Weather(timeOfObservation, description, cityName, pressure, /*seaLevelPressure,*/ windSpeed, windDirection, temperature, apparentTemperature, clouds, humidity, precipitation, snowAmount);
    }
}
