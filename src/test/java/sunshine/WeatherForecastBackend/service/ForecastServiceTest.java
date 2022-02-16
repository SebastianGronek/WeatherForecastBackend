package sunshine.WeatherForecastBackend.service;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.client.RestTemplate;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.WeatherBitDTO;
import sunshine.WeatherForecastBackend.model.WeatherForecastAPIs;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForecastServiceTest {
    static WeatherBitImporter weatherBitImporter;
    static OpenWeatherMapImporter openWeatherMapImporter;
    static ForecastService forecastService;

    @BeforeAll
    static void prepareImportersAndForecastService() {
        weatherBitImporter = new WeatherBitImporter(new RestTemplate());
        openWeatherMapImporter = new OpenWeatherMapImporter(new RestTemplate());
        forecastService = new ForecastService(weatherBitImporter, openWeatherMapImporter);
    }

    @Mock
    WeatherBitImporter weatherBitImporterMocked;

    @Mock(lenient = true)
    OpenWeatherMapImporter openWeatherMapImporterMocked;

    @InjectMocks
    ForecastService forecastServiceMocked;

    @ParameterizedTest
    @CsvSource({"London, Weather_BIt, IMPERIAL", "Murmansk, OPen_WeaTher_map, SCIENTIFIC", "Warsaw, OPen_weather_map, SCIENTIFIC"})
    void shouldSendResponseForCityWithAllParametersSet(String city, String api, String units) {
        //given

        //when
        WeatherForecastAPIs chosenApi = WeatherForecastAPIs.valueOf(api.toUpperCase(Locale.ROOT));
        List<Forecast> result = forecastService.getForecastsFromChosenApi(city, chosenApi, units);
        //then
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({"London", "Murmansk", "Copenhagen", "Oslo", "Warsaw"})
    void shouldSendResponseForCityNotAllParameters(String city) {
        //given
        ForecastService forecastService = new ForecastService(weatherBitImporter, openWeatherMapImporter);
        //when
        List<Forecast> result = forecastService.getForecastsFromChosenApi(city, null, null);
        //then
        assertThat(result).isNotNull();
    }

    @Test
    void shouldChooseRightAPI() {
        //given
        String city = "London";
        WeatherForecastAPIs api = WeatherForecastAPIs.valueOf("WEATHER_BIT");
        String units = "IMPERIAL";
        List<Forecast> responseFromMockedImporter = List.of(Forecast.builder()
                .provider("WeatherBit")
                .lastObservationTime(LocalDateTime.now())
                .description(new String[]{"rain"})
                .cityName("London")
                .pressure(1024)
                .windSpeed(2.3)
                .windDirection(120)
                .temperature(43)
                .apparentTemperature(34)
                .clouds(50)
                .humidity(63)
                .precipitation(5)
                .snow(0)
                .build());
        when(weatherBitImporterMocked.importForecasts(city, units)).thenReturn(responseFromMockedImporter);
        when(openWeatherMapImporterMocked.importForecasts(city, units)).thenReturn(null);
        //when
        List<Forecast> result = forecastServiceMocked.getForecastsFromChosenApi(city, api, units);
        //then
        assertThat(result).isEqualTo(responseFromMockedImporter);
    }

    @Test
    void shouldThrowExceptionWhenCityIsNull() {
        //given
        when(openWeatherMapImporterMocked.importForecasts(null, null)).thenReturn(null);
        when(openWeatherMapImporterMocked.importForecasts(null, null)).thenReturn(null);
        //when

        //then
        assertThatThrownBy(() -> forecastServiceMocked.getForecastsFromChosenApi(null, null, null)).isInstanceOf(NullPointerException.class);
    }
}