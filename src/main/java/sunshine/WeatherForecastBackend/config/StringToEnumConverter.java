package sunshine.WeatherForecastBackend.config;



import org.springframework.core.convert.converter.Converter;
import sunshine.WeatherForecastBackend.model.WeatherForecastAPIs;

import java.util.Locale;

public class StringToEnumConverter implements Converter<String, WeatherForecastAPIs> {

    @Override
    public WeatherForecastAPIs convert(String source) {
        return WeatherForecastAPIs.valueOf(source.toUpperCase(Locale.ROOT));
    }
}
