package sunshine.WeatherForecastBackend.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import sunshine.WeatherForecastBackend.error.CannotImportWeatherFromExternalDatabaseException;
import sunshine.WeatherForecastBackend.model.Forecast;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ForecastServiceTest {
    @Mock
    WeatherBitImporter weatherBitImporter;

    @Mock(lenient = true)
    OpenWeatherMapImporter openWeatherMapImporter;

    @InjectMocks
    ForecastService forecastService;

    @ParameterizedTest
    @CsvSource({"London, Weather_BIt, IMPERIAL", "Murmansk, OPen_weather_map, SCIENTIFIC"})
    void shouldSendResponseForCityWithAllParametersSet(String city, String api, String units) {
        //given
        WeatherBitImporter weatherBitImporter = new WeatherBitImporter();
        OpenWeatherMapImporter openWeatherMapImporter = new OpenWeatherMapImporter();
        ForecastService forecastService = new ForecastService(weatherBitImporter, openWeatherMapImporter);
        //when
        List<Forecast> result = forecastService.getForecastsFromChosenApi(city, api, units);
        //then
        assertThat(result).isNotNull();
    }

    @ParameterizedTest
    @CsvSource({"London", "Murmansk", "Warsaw", "Oslo"})
    void shouldSendResponseForCityNotAllParameters(String city) {
        //given
        WeatherBitImporter weatherBitImporter = new WeatherBitImporter();
        OpenWeatherMapImporter openWeatherMapImporter = new OpenWeatherMapImporter();
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
        String api = "WEATHER_BIT";
        String units = "IMPERIAL";
        List<Forecast> responseFromMockedImporter = List.of(new Forecast("WeatherBit",
                LocalDateTime.now(),
                new String[]{"rain"},
                "London",
                1024,
                2.3,
                120,
                43,
                34,
                50,
                63,
                5,
                0
        ));
        when(weatherBitImporter.importForecasts(city, units)).thenReturn(responseFromMockedImporter);
        when(openWeatherMapImporter.importForecasts(city, units)).thenReturn(null);
        //when
        List<Forecast> result = forecastService.getForecastsFromChosenApi(city, api, units);
        //then
        assertThat(result).isEqualTo(responseFromMockedImporter);
    }

    @Test
    void shouldThrowExceptionWhenCityIsNull() {
        //given
        when(openWeatherMapImporter.importForecasts(null, null)).thenReturn(null);
        when(openWeatherMapImporter.importForecasts(null, null)).thenReturn(null);
        //when

        //then
        assertThatThrownBy(() -> forecastService.getForecastsFromChosenApi(null, null, null)).isInstanceOf(CannotImportWeatherFromExternalDatabaseException.class).hasMessage("No city was provided to get forecast");
    }
}