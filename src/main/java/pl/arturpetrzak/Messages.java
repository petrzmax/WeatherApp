package pl.arturpetrzak;

public class Messages {

    public static final String SUCCESS = "Data fetched successfully";

    // Messages headers
    public static final String FETCHING_LOCALIZATION = "Fetching current localization";
    public static final String FETCHING_CITY_ID = "Fetching city id";
    public static final String FETCHING_WEATHER_DATA= "Fetching weather data";

    // Services errors
    public static final String REQUEST_SYNTAX_ERROR = "Request syntax error occurred";
    public static final String API_AUTHORIZATION_ERROR = "API error occurred";
    public static final String SERVER_ERROR = "Server error occurred";
    public static final String UNEXPECTED_ERROR = "Unexpected error occurred";
    public static final String UNSUPPORTED_ERROR = "Unsupported fetching result";

    // Validation errors
    public static final String NO_CITY_NAME = "Provide city name to begin";
    public static final String CITY_NAME_TOO_LONG = "City name is too long. It should be shorter than 50 characters";
    public static final String COUNTRY_NAME_TOO_LONG = "Country name is too long. It should be shorter than 50 characters";
}
