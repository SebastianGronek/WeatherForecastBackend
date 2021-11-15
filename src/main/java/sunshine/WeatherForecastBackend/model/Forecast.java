package sunshine.WeatherForecastBackend.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Forecast {
    String provider;
    LocalDateTime lastObservationTime;
    String[] description;
    String cityName;
    double pressure;
//    double seaLevelPressure;
    double windSpeed;
    int windDirection;
    double temperature;
    double apparentTemperature;
    int clouds;
    double humidity;
    double precipitation;
    double snow;
}
