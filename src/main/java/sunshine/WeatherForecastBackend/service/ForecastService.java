package sunshine.WeatherForecastBackend.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherForecastAPIs;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ForecastService {
    private final WeatherBitImporter weatherBitImporter;
    private final OpenWeatherMapImporter openWeatherMapImporter;

    private Map<WeatherForecastAPIs, ForecastImporter> importersMap;

    @Autowired
    public ForecastService(WeatherBitImporter weatherBitImporter, OpenWeatherMapImporter openWeatherMapImporter) {
        this.weatherBitImporter = weatherBitImporter;
        this.openWeatherMapImporter = openWeatherMapImporter;
        importersMap = Map.of(WeatherForecastAPIs.WEATHER_BIT, weatherBitImporter, WeatherForecastAPIs.OPEN_WEATHER_MAP, openWeatherMapImporter);
    }

    public List<Forecast> getForecastsFromChosenApi(String city, String weatherApi, String units) {
        if (weatherApi == null) {
            return getForecastsFromAllApis(city, units);
        }
        WeatherForecastAPIs w = getWeatherForecastAPIFromString(weatherApi);
        return getForecastsFromParticularApi(w, city, units);
    }

    private List<Forecast> getForecastsFromAllApis(String city, String units) {
        return importersMap.values().stream().flatMap(importer -> importer.importForecasts(city, units).stream()).collect(Collectors.toList());
    }

    private WeatherForecastAPIs getWeatherForecastAPIFromString(String weatherApi) {
        try {
            return WeatherForecastAPIs.valueOf(weatherApi.toUpperCase(Locale.ROOT));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("There is no such weather provider in our api");
        }
    }

    private List<Forecast> getForecastsFromParticularApi(WeatherForecastAPIs w, String city, String units) {
        for (Map.Entry<WeatherForecastAPIs, ForecastImporter> e : importersMap.entrySet()) {
            if (w == e.getKey()) {
                return e.getValue().importForecasts(city, units);
            }
        }
        throw new CannotImportWeatherFromExternalDatabaseException("This api has no proper importer");
    }
}
