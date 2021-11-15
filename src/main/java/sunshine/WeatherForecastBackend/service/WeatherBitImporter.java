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

    public Weather importWeatherConditionsFromOWeatherBit() {
        String uri = " https://api.weatherbit.io/v2.0/current?access_key={apiAccessKey}&cityName={}";
        String apiAccessKey = "6cbd14af64704f099273d45e0940e8cb";
        WeatherBitDTO weatherBitDTO = restTemplate.getForObject(uri, WeatherBitDTO.class, apiAccessKey);
        if (weatherBitDTO != null) {
            return WeatherBitMapper.convertToWeather(weatherBitDTO);
        } else {
            throw new CannotImportWeatherFromExternalDatabaseException("Cannot get weather data from WeatherBit");
        }
    }
}
