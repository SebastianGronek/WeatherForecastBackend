package sunshine.WeatherForecastBackend.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;

import java.util.List;

import static sunshine.WeatherForecastBackend.model.Units.validateUnit;

@Service
@RequiredArgsConstructor
public class WeatherBitImporter implements ForecastImporter {
    private final RestTemplate restTemplate;

    private final WeatherBitMapper weatherBitMapper;

    @Autowired
    public WeatherBitImporter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.weatherBitMapper = new WeatherBitMapper();
    }

    public List<Forecast> importForecasts(String city, String units) {
        WeatherBitDTO weatherBitDTO = importDto(city, determineUnit(units));
        return convertDtoToForecasts(weatherBitDTO);
    }

    private WeatherBitDTO importDto(String city, String units) {
        String uri = "https://api.weatherbit.io/v2.0/current?key={apiAccessKey}&city={city}&units={units}";
        String apiAccessKey = "6cbd14af64704f099273d45e0940e8cb";
        try {
            return restTemplate.getForObject(uri, WeatherBitDTO.class, apiAccessKey, city, units);
        } catch (HttpClientErrorException e) {
            throw new CannotImportWeatherFromExternalDatabaseException("No city with this name was found in WeatherBit API");
        }
    }

    private List<Forecast> convertDtoToForecasts(WeatherBitDTO weatherBitDTO) {
        return weatherBitMapper.convertToForecasts(weatherBitDTO);
    }

    private String determineUnit(String units) {
        switch (validateUnit(units)) {
            case IMPERIAL:
                return "I";
            case SCIENTIFIC:
                return "S";
            case METRIC:
                return "M";
            default:
                throw new IllegalArgumentException("These units are not used in external APIs");
        }
    }
}
