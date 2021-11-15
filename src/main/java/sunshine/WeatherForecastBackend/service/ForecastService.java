package sunshine.WeatherForecastBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherForecastAPIs;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class ForecastService {
    WeatherBitImporter weatherBitImporter;
    OpenWeatherMapImporter openWeatherMapImporter;

    @Autowired
    public ForecastService(WeatherBitImporter weatherBitImporter, OpenWeatherMapImporter openWeatherMapImporter) {
        this.weatherBitImporter = weatherBitImporter;
        this.openWeatherMapImporter = openWeatherMapImporter;
    }

    public List<Forecast> getForecastsFromChosenApi(String city, String weatherApi, String units) {
        if (weatherApi == null) {
            return getForecastsFromBothApis(city, units);
        }
        WeatherForecastAPIs w = getWeatherForecastAPIFromString(weatherApi);
        return getForecastsFromParticularApi(w, city, units);
    }

    private List<Forecast> getForecastsFromBothApis(String city, String units) {
        List<Forecast> result = new ArrayList<>();
        result.addAll(openWeatherMapImporter.importWeatherConditionsFromOpenWeatherMap(city, units));
        result.addAll(weatherBitImporter.importWeatherConditionsFromOWeatherBit(city, units));
        return result;
    }

    private WeatherForecastAPIs getWeatherForecastAPIFromString(String weatherApi) {
        try {
            return WeatherForecastAPIs.valueOf(weatherApi.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("There is no such weather provider in our api");
        }
    }

    private List<Forecast> getForecastsFromParticularApi(WeatherForecastAPIs w, String city, String units) {
        switch (w) {
            case OPEN_WEATHER_MAP:
                return openWeatherMapImporter.importWeatherConditionsFromOpenWeatherMap(city, units);
            case WEATHER_BIT:
                return weatherBitImporter.importWeatherConditionsFromOWeatherBit(city, units);
            default:
                return null;
        }
    }
}
