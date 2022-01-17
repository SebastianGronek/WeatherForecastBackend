package sunshine.WeatherForecastBackend.service;

import org.springframework.stereotype.Service;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherForecastAPIs;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ForecastService {

    private final Map<WeatherForecastAPIs, ForecastImporter> importersMap;

    public ForecastService(WeatherBitImporter weatherBitImporter, OpenWeatherMapImporter openWeatherMapImporter) {
        importersMap = Map.of(WeatherForecastAPIs.WEATHER_BIT, weatherBitImporter, WeatherForecastAPIs.OPEN_WEATHER_MAP, openWeatherMapImporter);
    }

    public List<Forecast> getForecastsFromChosenApi(String city, WeatherForecastAPIs weatherApi, String units) {
        if (weatherApi == null) {
            return getForecastsFromAllApis(city, units);
        }
        return getForecastsFromParticularApi(weatherApi, city, units);
    }

    private List<Forecast> getForecastsFromAllApis(String city, String units) {
        return importersMap.values().stream().flatMap(importer -> importer.importForecasts(city, units).stream()).collect(Collectors.toList());
    }

    private List<Forecast> getForecastsFromParticularApi(WeatherForecastAPIs w, String city, String units) {
        Optional<List<Forecast>> listOfForecasts = Optional.ofNullable(importersMap.get(w).importForecasts(city, units));
        if (listOfForecasts.isPresent()) {
            return listOfForecasts.get();
        } else {
            throw new CannotImportWeatherFromExternalDatabaseException("This api has no proper importer");
        }
    }
}
