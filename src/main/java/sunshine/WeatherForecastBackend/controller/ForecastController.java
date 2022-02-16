package sunshine.WeatherForecastBackend.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import sunshine.WeatherForecastBackend.model.Forecast;
import sunshine.WeatherForecastBackend.model.Units;
import sunshine.WeatherForecastBackend.model.WeatherForecastAPIs;
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

    @GetMapping("/getForecast")
    public List<Forecast> getForecastFromChosenProviders(@RequestParam String city, @RequestParam(required = false)
               WeatherForecastAPIs chosenApi, @RequestParam(required = false) String units) {
        return forecastService.getForecastsFromChosenApi(city, chosenApi, units);
    }
}
