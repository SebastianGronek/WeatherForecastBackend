package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;
import sunshine.WeatherForecastBackend.model.Weather;

@Service
@RequiredArgsConstructor
@Slf4j
public class OpenWeatherMapImporter {
    private final RestTemplate restTemplate = new RestTemplate();

    private final OpenWeatherMapMapper openWeatherMapMapper = new OpenWeatherMapMapper();

    public Weather importWeatherConditionsFromOpenWeatherMap(String cityName, String unit) {
        String uri = "http://api.openweathermap.org/data/2.5/weather?q={cityName}&appid={apiAccessKey}&units={unit}";
        String apiAccessKey = "95aeddeab78810ec4e5e46ed73f205cd";
        System.out.println("works to this moment");
        if (unit == null) {
            unit = "metric";
        }
        OpenWeatherMapDTO openWeatherMapDTO = restTemplate.getForObject(uri, OpenWeatherMapDTO.class, cityName, apiAccessKey, unit);
        System.out.println("api key: " + apiAccessKey);
        if (openWeatherMapDTO != null) {
            return openWeatherMapMapper.convertToWeather(openWeatherMapDTO);
        } else {
            throw new CannotImportWeatherFromExternalDatabaseException("Cannot get weather data from OpenWeatherMap");
        }
    }
}
