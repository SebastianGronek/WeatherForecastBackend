package sunshine.WeatherForecastBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.WeatherForecastBackend.model.Weather;
import sunshine.WeatherForecastBackend.service.OpenWeatherMapImporter;
import sunshine.WeatherForecastBackend.service.WeatherBitImporter;

@RestController
@RequiredArgsConstructor
public class WeatherController {
    WeatherBitImporter weatherBitImporter;
    OpenWeatherMapImporter openWeatherMapImporter;

    @Autowired
    public WeatherController(WeatherBitImporter weatherBitImporter, OpenWeatherMapImporter openWeatherMapImporter) {
        this.weatherBitImporter = weatherBitImporter;
        this.openWeatherMapImporter = openWeatherMapImporter;
    }

    @GetMapping("/getWeatherBit")
    public Weather getWeatherFromWeatherBitImporterByCityName(@RequestParam(name = "cityName") String cityName) {
        return weatherBitImporter.importWeatherConditionsFromOWeatherBit();
    }

    @GetMapping("/getWeatherMap")
    public Weather getWeatherFromOpenWeatherImporterByCityName(@RequestParam(name = "cityName") String cityName, @RequestParam(name = "units", required = false) String units) {
        return openWeatherMapImporter.importWeatherConditionsFromOpenWeatherMap(cityName, units);
    }
}
