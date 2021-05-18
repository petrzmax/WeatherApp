package pl.arturpetrzak.controller.services;

import org.json.JSONObject;
import pl.arturpetrzak.Config;

public class FetchCurrentCityNameService extends BaseApiService{
    protected final String API_KEY_PREFIX = "access_key=";

    public FetchCurrentCityNameService() {
        super();
        url = Config.getCurrentCityByIpApiUrl() + "?fields=location&" + API_KEY_PREFIX + Config.getIpstackApiKey();
    }

    public String getCountryName() {
        return jsonResponse.getString("country_name");
    }

    public String getCityName() {
        return jsonResponse.getJSONObject("location").getString("capital");
    }

    @Override
    protected JSONObject parseResponse(String response) {
        return new JSONObject(response);
    }
}
