package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapImporter implements ForecastImporter {
    private final RestTemplate restTemplate = new RestTemplate();

    private final OpenWeatherMapMapper openWeatherMapMapper = new OpenWeatherMapMapper();

    public List<Forecast> importForecasts(String city, String units) {
        OpenWeatherMapDTO openWeatherMapDTO = importDto(city, units);
        return convertDtoToWeather(openWeatherMapDTO);
    }


    private OpenWeatherMapDTO importDto(String city, String units) {
        String uri = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiAccessKey}&units={units}";
        String apiAccessKey = "95aeddeab78810ec4e5e46ed73f205cd";
        System.out.println("works to this moment");
        if (units == null) {
            units = "metric";
        }
        return restTemplate.getForObject(uri, OpenWeatherMapDTO.class, city, apiAccessKey, units);
    }

    private List<Forecast> convertDtoToWeather(OpenWeatherMapDTO openWeatherMapDTO) {
        if (openWeatherMapDTO != null) {
            return openWeatherMapMapper.convertToForecasts(openWeatherMapDTO);
        } else {
            throw new CannotImportWeatherFromExternalDatabaseException("Cannot get weather data from OpenWeatherMap");
        }
    }
}
