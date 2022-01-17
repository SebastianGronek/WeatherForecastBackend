package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;
import sunshine.WeatherForecastBackend.model.Units;

import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class OpenWeatherMapImporter implements ForecastImporter {
    @Autowired
    private RestTemplate restTemplate;

    private final OpenWeatherMapMapper openWeatherMapMapper = new OpenWeatherMapMapper();

    public List<Forecast> importForecasts(String city, String units) {
        OpenWeatherMapDTO openWeatherMapDTO = importDto(city, determineUnit(units));
        return convertDtoToWeather(openWeatherMapDTO);
    }


    private OpenWeatherMapDTO importDto(String city, String units) {
        String uri = "http://api.openweathermap.org/data/2.5/weather?q={city}&appid={apiAccessKey}&units={units}";
        String apiAccessKey = "95aeddeab78810ec4e5e46ed73f205cd";
        try {
            return restTemplate.getForObject(uri, OpenWeatherMapDTO.class, city, apiAccessKey, units);
        } catch (HttpClientErrorException e) {
            throw new CannotImportWeatherFromExternalDatabaseException("No city with this name was found in OpenWeatherMap API");
        }
    }

    private List<Forecast> convertDtoToWeather(OpenWeatherMapDTO openWeatherMapDTO) {
        return openWeatherMapMapper.convertToForecasts(openWeatherMapDTO);
    }

    private String determineUnit(String units) {
        if (units == null) {
            return "metric";
        }
        units = units.toUpperCase(Locale.ROOT);
        if (Units.valueOf(units) == Units.IMPERIAL) {
            return "imperial";
        } else if (Units.valueOf(units) == Units.SCIENTIFIC) {
            return "standard";
        } else {
            throw new IllegalArgumentException("These units are not used in external APIs");
        }
    }
}
