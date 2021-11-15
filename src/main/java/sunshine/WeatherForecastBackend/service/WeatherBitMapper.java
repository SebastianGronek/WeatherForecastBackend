package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.Weather;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

@RequiredArgsConstructor
public class WeatherBitMapper {
    public Weather convertToWeather(WeatherBitDTO weatherBitDTO) {
        System.out.println("Count in WeatherBit: "+weatherBitDTO.getCount());
        WeatherBitDTO.Data[] data = weatherBitDTO.getData();
        WeatherBitDTO.Data currentData = data[0];
        long time = Integer.parseInt(currentData.getTs()) * 1000L;
        LocalDateTime timeOfObservation = LocalDateTime.ofInstant(Instant.ofEpochMilli(time),
                TimeZone.getDefault().toZoneId());
        String description = currentData.getWeather().getDescription();
        String cityName = currentData.getCity_name();
        double pressure = Double.parseDouble(currentData.getPres());
        double seaLevelPressure = Double.parseDouble(currentData.getSlp());
        double windSpeed = Double.parseDouble(currentData.getWind_spd());
        int windDirection = Integer.parseInt(currentData.getWind_dir());
        double temperature = Double.parseDouble(currentData.getTemp());
        double apparentTemperature = Double.parseDouble(currentData.getApp_temp());
        int clouds = Integer.parseInt(currentData.getClouds());
        double humidity = Double.parseDouble(currentData.getRh());
        double precipitation = Double.parseDouble(currentData.getPrecip());
        double snow = Double.parseDouble(currentData.getSnow());
        return new Weather(timeOfObservation, description, cityName, pressure, /*seaLevelPressure,*/ windSpeed, windDirection, temperature, apparentTemperature, clouds, humidity, precipitation, snow);
    }
}
