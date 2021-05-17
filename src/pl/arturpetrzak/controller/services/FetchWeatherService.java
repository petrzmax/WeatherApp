package pl.arturpetrzak.controller.services;

import org.json.JSONObject;
import pl.arturpetrzak.Config;

public class FetchWeatherService extends BaseApiService {

    public FetchWeatherService(String cityId) {
        super();
        url = Config.getDailyWeatherForecastApiUrl() + cityId + "?" + API_KEY_PREFIX + Config.getAccuWeatherApiKey();
    }

    public JSONObject getWeatherData() {
        return jsonResponse;
    }

    @Override
    protected JSONObject parseResponse(String response) {
        return new JSONObject(response);
    }
}
