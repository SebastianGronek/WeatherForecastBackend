package sunshine.WeatherForecastBackend.model;

import java.util.Locale;

public enum Units {
    METRIC, SCIENTIFIC, IMPERIAL;

    public static Units validateUnit(String units) {
        if (units == null) {
            return Units.METRIC;
        }
        units = units.toUpperCase(Locale.ROOT);
        return Units.valueOf(units);
    }
}
