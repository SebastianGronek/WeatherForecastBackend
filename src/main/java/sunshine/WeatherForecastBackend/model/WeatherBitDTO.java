package sunshine.WeatherForecastBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitDTO {
    String ts;
    String cityName;
    String pres;
    String slp;
    String windSpd;
    String windDir;
    String temp;
    String appTemp;
    String rh;
    String clouds;
    Weather weather;
    String precip;
    String snow;

    @Data
    public static class Weather {
        String description;
    }
}
