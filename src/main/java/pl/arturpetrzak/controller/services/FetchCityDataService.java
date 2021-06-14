package pl.arturpetrzak.controller.services;

import okhttp3.OkHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.arturpetrzak.Config;

public class FetchCityDataService extends BaseApiService {

    private String country;
    private String city;

    public FetchCityDataService(OkHttpClient okHttpClient) {
        super(okHttpClient);
    }

    public String getCityId() {
        return jsonResponse.get("Key").toString();
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    protected void buildUrl() {
        url = Config.getCitySearchApiUrl() + "?" + CITY_PREFIX + country + "," + city + "&" + API_KEY_PREFIX + Config.getAccuWeatherApiKey();
    }

    @Override
    protected JSONObject parseResponse(String response) {
        JSONArray jsonArray = new JSONArray(response);
        if (jsonArray.isEmpty()) {
            return null;
        }
        return (JSONObject) jsonArray.get(0);
    }
}
