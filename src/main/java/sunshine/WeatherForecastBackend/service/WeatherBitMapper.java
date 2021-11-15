package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.Weather;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RequiredArgsConstructor
public class WeatherBitMapper {
    public static Weather convertToWeather(WeatherBitDTO weatherBitDTO) {
        LocalDateTime timeOfObservation = LocalDateTime.parse(weatherBitDTO.getTs(), DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String description = weatherBitDTO.getWeather().getDescription();
        String cityName = weatherBitDTO.getCityName();
        double pressure = Double.parseDouble(weatherBitDTO.getPres());
        double seaLevelPressure = Double.parseDouble(weatherBitDTO.getSlp());
        double windSpeed = Double.parseDouble(weatherBitDTO.getWindSpd());
        int windDirection = Integer.parseInt(weatherBitDTO.getWindDir());
        double temperature = Double.parseDouble(weatherBitDTO.getTemp());
        double apparentTemperature = Double.parseDouble(weatherBitDTO.getAppTemp());
        int clouds = Integer.parseInt(weatherBitDTO.getClouds());
        int humidity = Integer.parseInt(weatherBitDTO.getRh());
        double precipitation = Double.parseDouble(weatherBitDTO.getPrecip());
        double snow = Double.parseDouble(weatherBitDTO.getSnow());
        return new Weather(timeOfObservation, description, cityName, pressure, /*seaLevelPressure,*/ windSpeed, windDirection, temperature, apparentTemperature, clouds, humidity, precipitation, snow);
    }
}
