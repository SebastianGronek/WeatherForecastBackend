package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;
import sunshine.WeatherForecastBackend.model.WeatherDto;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@RequiredArgsConstructor
public class WeatherBitMapper implements ForecastMapper {
    public List<Forecast> convertToForecasts(WeatherDto weatherBitDTO) {
        WeatherBitDTO.Data[] data = ((WeatherBitDTO) weatherBitDTO).getData();
        List<Forecast> result = new ArrayList<>();
        for (WeatherBitDTO.Data currentData : data) {
            result.add(convertSingleDataToForecast(currentData));
        }
        return result;
    }

    private static Forecast convertSingleDataToForecast(WeatherBitDTO.Data currentData) {
        String provider = "WeatherBit";
        LocalDateTime timeOfObservation = LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.parseInt(currentData.getTs())),
                TimeZone.getDefault().toZoneId());
        String[] description = new String[]{currentData.getWeather().getDescription()};
        String cityName = currentData.getCity_name();
        double pressure = Double.parseDouble(currentData.getPres());
        double windSpeed = Double.parseDouble(currentData.getWind_spd());
        int windDirection = Integer.parseInt(currentData.getWind_dir());
        double temperature = Double.parseDouble(currentData.getTemp());
        double apparentTemperature = Double.parseDouble(currentData.getApp_temp());
        int clouds = Integer.parseInt(currentData.getClouds());
        double humidity = Double.parseDouble(currentData.getRh());
        double precipitation = Double.parseDouble(currentData.getPrecip());
        double snow = Double.parseDouble(currentData.getSnow());
        return new Forecast(provider, timeOfObservation, description, cityName, pressure, windSpeed, windDirection, temperature, apparentTemperature, clouds, humidity, precipitation, snow);
    }
}
