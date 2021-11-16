package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.OpenWeatherMapDTO;
import sunshine.WeatherForecastBackend.model.Units;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;

import java.util.List;
import java.util.Locale;

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
        units = determineUnit(units);
        try {
            return restTemplate.getForObject(uri, WeatherBitDTO.class, apiAccessKey, city, units);
        } catch (HttpClientErrorException e) {
            throw new CannotImportWeatherFromExternalDatabaseException("No city with this name was found in WeatherBit API");
        }

    }

    private List<Forecast> convertDtoToWeather(WeatherBitDTO weatherBitDTO) {
        if (weatherBitDTO != null) {
            return weatherBitMapper.convertToForecasts(weatherBitDTO);
        } else {
            throw new CannotImportWeatherFromExternalDatabaseException("Cannot get weather data from WeatherBit");
        }
    }

    private String determineUnit(String units) {
        if (units == null) {
            return "M";
        }
        units = units.toUpperCase(Locale.ROOT);
        if (Units.valueOf(units) == Units.IMPERIAL) {
            return "I";
        } else if (Units.valueOf(units) == Units.SCIENTIFIC) {
            return "S";
        } else {
            throw new IllegalArgumentException("These units are not used int external APIs");
        }
    }
}
