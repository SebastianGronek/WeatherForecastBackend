package sunshine.WeatherForecastBackend.error;

public class CannotImportWeatherFromExternalDatabaseException extends RuntimeException {
    public CannotImportWeatherFromExternalDatabaseException(String message) {
        super(message);
    }
}
