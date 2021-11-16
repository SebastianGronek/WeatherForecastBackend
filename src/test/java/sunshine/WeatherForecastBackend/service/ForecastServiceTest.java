package sunshine.WeatherForecastBackend.service;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import sunshine.WeatherForecastBackend.model.Forecast;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ForecastServiceTest {

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
}