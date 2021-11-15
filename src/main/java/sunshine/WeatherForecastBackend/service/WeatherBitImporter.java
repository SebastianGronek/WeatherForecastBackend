package sunshine.WeatherForecastBackend.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Weather;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;

@Service
public class WeatherBitImporter {
    private final RestTemplate restTemplate = new RestTemplate();
    private final WeatherBitMapper weatherBitMapper = new WeatherBitMapper();

    public Weather importWeatherConditionsFromOWeatherBit(String city, String units) {
        String uri = "https://api.weatherbit.io/v2.0/current?key={apiAccessKey}&city={city}&units={units}";
        String apiAccessKey = "6cbd14af64704f099273d45e0940e8cb";
        if (units == null) {
            units = "metric";
        }
        WeatherBitDTO weatherBitDTO = restTemplate.getForObject(uri, WeatherBitDTO.class, apiAccessKey, city, units);
        if (weatherBitDTO != null) {
            return weatherBitMapper.convertToWeather(weatherBitDTO);
        } else {
            throw new CannotImportWeatherFromExternalDatabaseException("Cannot get weather data from WeatherBit");
        }
    }
}
