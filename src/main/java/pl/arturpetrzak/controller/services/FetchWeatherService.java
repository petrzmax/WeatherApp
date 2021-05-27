package pl.arturpetrzak.controller.services;

import org.json.JSONObject;
import pl.arturpetrzak.Config;

public class FetchWeatherService extends BaseApiService {
    private final String METRIC_PREFIX = "metric=";

    public FetchWeatherService(String cityId, boolean metric) {
        super();
        url = Config.getDailyWeatherForecastApiUrl() + cityId + "?" + METRIC_PREFIX + metric + "&" + API_KEY_PREFIX + Config.getAccuWeatherApiKey();
    }

    public JSONObject getWeatherData() {
        return jsonResponse;
    }

    @Override
    protected JSONObject parseResponse(String response) {
        return new JSONObject(response);
    }
}
