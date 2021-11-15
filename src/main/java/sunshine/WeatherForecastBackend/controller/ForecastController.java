package sunshine.WeatherForecastBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.service.OpenWeatherMapImporter;
import sunshine.WeatherForecastBackend.service.WeatherBitImporter;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ForecastController {
    WeatherBitImporter weatherBitImporter;
    OpenWeatherMapImporter openWeatherMapImporter;

    @Autowired
    public ForecastController(WeatherBitImporter weatherBitImporter, OpenWeatherMapImporter openWeatherMapImporter) {
        this.weatherBitImporter = weatherBitImporter;
        this.openWeatherMapImporter = openWeatherMapImporter;
    }

    @GetMapping("/getWeatherBit")
    public List<Forecast> getWeatherFromWeatherBitImporterByCityName(@RequestParam(name = "city") String city, @RequestParam(name = "units", required = false) String units) {
        return weatherBitImporter.importWeatherConditionsFromOWeatherBit(city, units);
    }

    @GetMapping("/getWeatherMap")
    public Forecast getWeatherFromOpenWeatherImporterByCityName(@RequestParam(name = "city") String city, @RequestParam(name = "units", required = false) String units) {
        return openWeatherMapImporter.importWeatherConditionsFromOpenWeatherMap(city, units);
    }

    @GetMapping("/forecastFromAll")
    public List<Forecast> getForecastFromAllProviders(@RequestParam(name = "city") String city, @RequestParam(name = "units", required = false) String units) {
        List<Forecast> result = new ArrayList<>();
        result.add(openWeatherMapImporter.importWeatherConditionsFromOpenWeatherMap(city, units));
        result.addAll(weatherBitImporter.importWeatherConditionsFromOWeatherBit(city, units));
        return result;
    }
}
