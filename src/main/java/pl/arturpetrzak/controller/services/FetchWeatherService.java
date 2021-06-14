package pl.arturpetrzak.controller.services;

import org.json.JSONObject;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.Languages;

public class FetchWeatherService extends BaseApiService {
    private final String METRIC_PREFIX = "metric=";
    private final String LANGUAGE_PREFIX = "language=";

    public FetchWeatherService(String cityId, boolean isUsingMetricUnits, Languages language) {
        super();
        url = Config.getDailyWeatherForecastApiUrl() + cityId + "?" + METRIC_PREFIX + isUsingMetricUnits + "&" + LANGUAGE_PREFIX + language + "&" + API_KEY_PREFIX + Config.getAccuWeatherApiKey();
    }

    public JSONObject getWeatherData() {
        return jsonResponse;
    }

    @Override
    protected JSONObject parseResponse(String response) {
        return new JSONObject(response);
    }
}
