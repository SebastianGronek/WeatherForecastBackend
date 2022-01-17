package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TimeZone;

@RequiredArgsConstructor
public class WeatherBitMapper {
    public List<Forecast> convertToForecasts(WeatherBitDTO weatherBitDTO) {
        WeatherBitDTO.Data[] data = weatherBitDTO.getData();
        List<Forecast> result = new ArrayList<>();
        for (WeatherBitDTO.Data currentData : data) {
            result.add(convertSingleDataToForecast(currentData));
        }
        return result;
    }

    private static Forecast convertSingleDataToForecast(WeatherBitDTO.Data currentData) {
        return Forecast.builder()
                .provider("WeatherBit")
                .lastObservationTime(LocalDateTime.ofInstant(Instant.ofEpochSecond(Integer.parseInt(currentData.getTs())),
                        TimeZone.getDefault().toZoneId()))
                .description(new String[]{currentData.getWeather().getDescription()})
                .cityName(currentData.getCity_name())
                .pressure(Double.parseDouble(currentData.getPres()))
                .windSpeed(Double.parseDouble(currentData.getWind_spd()))
                .windDirection(Integer.parseInt(currentData.getWind_dir()))
                .temperature(Double.parseDouble(currentData.getTemp()))
                .apparentTemperature(Double.parseDouble(currentData.getApp_temp()))
                .clouds(Integer.parseInt(currentData.getClouds()))
                .humidity(Double.parseDouble(currentData.getRh()))
                .precipitation(Double.parseDouble(currentData.getPrecip()))
                .snow(Double.parseDouble(currentData.getSnow()))
                .build();
    }
}
