package pl.arturpetrzak.controller.services;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.arturpetrzak.Config;

public class FetchCityDataService extends BaseApiService {

    public FetchCityDataService(String city) {
        super();
        url = Config.getCitySearchApiUrl() + "?" + CITY_PREFIX + city + "&" + API_KEY_PREFIX + Config.getApiKey();
    }

    public String getCityId() {
        JSONArray jsonArray = new JSONArray(jsonResponse);
        JSONObject jsonObject = (JSONObject) jsonArray.get(0);

        return jsonObject.get("Key").toString();
    }
}
