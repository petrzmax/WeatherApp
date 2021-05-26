package pl.arturpetrzak.controller.services;

import org.json.JSONObject;
import pl.arturpetrzak.Config;

public class FetchCurrentLocalizationService extends BaseApiService{
    protected final String API_KEY_PREFIX = "access_key=";

    public FetchCurrentLocalizationService() {
        super();
        url = Config.getCurrentCityByIpApiUrl() + "?" + API_KEY_PREFIX + Config.getIpstackApiKey();
    }

    public String getCountry() {
        return jsonResponse.getString("country_name");
    }

    public String getCity() {
        return jsonResponse.getJSONObject("location").getString("capital");
    }

    @Override
    protected JSONObject parseResponse(String response) {
        return new JSONObject(response);
    }
}
