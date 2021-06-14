package pl.arturpetrzak;

public class Messages {

    public static final String SUCCESS = "Data fetched successfully";

    // Messages headers
    public static final String FETCHING_LOCALIZATION = "Fetching current localization";
    public static final String FETCHING_CITY_ID = "Fetching city id";
    public static final String FETCHING_WEATHER_DATA = "Fetching weather data";

    // Services errors
    public static final String REQUEST_SYNTAX_ERROR = "Request syntax error occurred";
    public static final String RESPONSE_EMPTY = "Can't find any data about your request";
    public static final String API_AUTHORIZATION_ERROR = "API error occurred";
    public static final String SERVER_ERROR = "Server error occurred";
    public static final String UNEXPECTED_ERROR = "Unexpected error occurred";
    public static final String UNSUPPORTED_ERROR = "Unsupported fetching result";

    // Validation errors
    public static final String NO_CITY_NAME = "Provide city name to begin";
    public static final String CITY_NAME_TOO_LONG = "City name is too long. It should be shorter than 50 characters";
    public static final String CITY_NAME_NO_NUMBERS = "City name can't contain any number";
    public static final String CITY_NAME_NO_SPECIAL_CHARACTERS = "City name can't contain any special characters";
    public static final String COUNTRY_NAME_TOO_LONG = "Country name is too long. It should be shorter than 50 characters";
    public static final String COUNTRY_NAME_NO_NUMBERS = "Country name can't contain any number";
    public static final String COUNTRY_NAME_NO_SPECIAL_CHARACTERS = "Country name can't contain any special characters";
    public static final String API_KEY_NO_SPECIAL_CHARACTERS = "API key can't contain any special characters";
    public static final String API_KEY_TOO_LONG = "API key is too long. It should be shorter than 50 characters";

    // Others
    public static final String DIALOG_TITLE = "Information Dialog";
    private static String TEMPERATURE_REPRESENTATION_TEMPLATE = "Temperatures:\nMax: %2$s °%3$s\nMin: %1$s °%3$s";

    public static String getTemperatureRepresentation(String minimumTemperature, String maximumTemperature, String unit) {
        return String.format(
                TEMPERATURE_REPRESENTATION_TEMPLATE,
                minimumTemperature,
                maximumTemperature,
                unit
        );
    }
}
