package sunshine.WeatherForecastBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.service.ForecastService;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ForecastController {
    ForecastService forecastService;

    @Autowired
    public ForecastController(ForecastService forecastService) {
        this.forecastService = forecastService;
    }

   /* @GetMapping("/getWeatherBit")
    public List<Forecast> getWeatherFromWeatherBitImporterByCityName(@RequestParam(name = "city") String city, @RequestParam(name = "units", required = false) String units) {
        return weatherBitImporter.importWeatherConditionsFromOWeatherBit(city, units);
    }

    @GetMapping("/getWeatherMap")
    public Forecast getWeatherFromOpenWeatherImporterByCityName(@RequestParam(name = "city") String city, @RequestParam(name = "units", required = false) String units) {
        return openWeatherMapImporter.importWeatherConditionsFromOpenWeatherMap(city, units);
    }*/

    @GetMapping("/getForecast")
    public List<Forecast> getForecastFromAllProviders(@RequestParam(name = "city") String city, @RequestParam(name = "api", required = false) String chosenApi, @RequestParam(name = "units", required = false) String units) {
        return forecastService.getForecastsFromChosenApi(city, chosenApi, units);
    }
}
