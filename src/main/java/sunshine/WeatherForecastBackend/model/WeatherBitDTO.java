package sunshine.WeatherForecastBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherBitDTO {
    String count;
    Data[] data;

    @lombok.Data
    public static class Data {
        String ts;
        String city_name;
        String pres;
        String slp;
        String wind_spd;
        String wind_dir;
        String temp;
        String app_temp;
        String rh;
        String clouds;
        Weather weather;
        String precip;
        String snow;

        @lombok.Data
        public static class Weather {
            String description;
        }
    }
}

