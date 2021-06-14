package pl.arturpetrzak.controller.services;

import okhttp3.OkHttpClient;
import org.json.JSONObject;
import pl.arturpetrzak.Config;

public class FetchCurrentLocalizationService extends BaseApiService {
    protected final String API_KEY_PREFIX = "access_key=";

    public FetchCurrentLocalizationService(OkHttpClient okHttpClient) {
        super(okHttpClient);
        url = Config.getCurrentCityByIpApiUrl() + "?" + API_KEY_PREFIX + Config.getIpstackApiKey();
    }

    public String getCountry() {
        return jsonResponse.getString("country_name");
    }

    public String getCity() {
        return jsonResponse.getString("city");
    }

    @Override
    protected void buildUrl() {}

    @Override
    protected JSONObject parseResponse(String response) throws Exception {
        JSONObject jsonObject = new JSONObject(response);

        if (jsonObject.has("success") && !jsonObject.getBoolean("success")) {
            jsonObject = jsonObject.getJSONObject("error");
            throw new Exception("HttpRequestCode: " + jsonObject.getInt("code") + " - " + jsonObject.getString("info"));
        }

        return jsonObject;
    }
}
