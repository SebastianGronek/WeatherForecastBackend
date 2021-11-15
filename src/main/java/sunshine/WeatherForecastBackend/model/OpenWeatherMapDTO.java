package sunshine.WeatherForecastBackend.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class OpenWeatherMapDTO {
    WeatherInMap[] weather;
    Main main;
    Wind wind;
    Clouds clouds;
    Rain rain;
    Snow snow;
    String dt;
    String name;

    @Data
    public static class WeatherInMap {
        String id;
        String main;
        String description;
    }

    @Data
    public static class Main {
        String temp;
        String feels_like;
        String pressure;
        String humidity;
        String temp_min;
        String temp_max;
//        String sea_level;
//        String grnd_level;
    }

    @Data
    public static class Wind {
        String speed;
        String deg;
        String gust;
    }

    @Data
    public static class Clouds {
        String all;
    }

    @Data
    public static class Rain {
        @JsonProperty("1h")
        String rain1h;
        @JsonProperty("3h")
        String rain3h;
    }

    @Data
    public static class Snow {
        @JsonProperty("1h")
        String snow1h;
        @JsonProperty("3h")
        String snow3h;
    }
}