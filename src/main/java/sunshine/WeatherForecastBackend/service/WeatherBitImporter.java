package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WeatherBitImporter implements ForecastImporter {
    private final RestTemplate restTemplate = new RestTemplate();
    private final WeatherBitMapper weatherBitMapper = new WeatherBitMapper();

    public List<Forecast> importForecasts(String city, String units) {
        WeatherBitDTO weatherBitDTO = importDto(city, units);
        return convertDtoToWeather(weatherBitDTO);
    }

    private WeatherBitDTO importDto(String city, String units) {
        String uri = "https://api.weatherbit.io/v2.0/current?key={apiAccessKey}&city={city}&units={units}";
        String apiAccessKey = "6cbd14af64704f099273d45e0940e8cb";
        if (units == null) {
            units = "metric";
        }
        return restTemplate.getForObject(uri, WeatherBitDTO.class, apiAccessKey, city, units);
    }

    private List<Forecast> convertDtoToWeather(WeatherBitDTO weatherBitDTO) {
        if (weatherBitDTO != null) {
            return weatherBitMapper.convertToForecasts(weatherBitDTO);
        } else {
            throw new CannotImportWeatherFromExternalDatabaseException("Cannot get weather data from WeatherBit");
        }
    }
}
