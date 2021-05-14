package pl.arturpetrzak.controller.services;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONArray;
import org.json.JSONObject;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.controller.FetchDataResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;


public class FetchCityDataService extends Service<FetchDataResult> {
    private final String API_KEY_PREFIX = "apikey=";
    private final String CITY_PREFIX = "q=";

    private String city;

    private OkHttpClient client;
    private JSONObject jsonResponse;

    public FetchCityDataService(String city) {
        this.city = city;
        client = new OkHttpClient();
    }

    @Override
    protected Task<FetchDataResult> createTask() {
        return new Task<FetchDataResult>() {
            @Override
            protected FetchDataResult call() throws Exception {
                return fetchCityData();
            }
        };
    }

    private FetchDataResult fetchCityData() {
        Response response = null;
        try {
            URL url = new URL(Config.getCitySearchApiUrl() + "?" + CITY_PREFIX + city + "&" + API_KEY_PREFIX + Config.getApiKey());
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if(response.code() != 200) {
                throw new IOException("HttpRequestCode: " + response.code());
            }
            JSONArray jsonArray = new JSONArray(response.body().string());

            jsonResponse = (JSONObject) jsonArray.get(0);

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return FetchDataResult.FAILED_BY_REQUEST_SYNTAX;
        } catch (IOException e) {
            e.printStackTrace();
            return getFetchDataResultByResponseCode(response.code());
        }
        return FetchDataResult.SUCCESS;
    }

    private FetchDataResult getFetchDataResultByResponseCode (int responseCode) {
        switch (responseCode) {
            case 400:
                return FetchDataResult.FAILED_BY_REQUEST_SYNTAX;
            case 401:
            case 403:
            case 503:
                return FetchDataResult.FAILED_BY_API_AUTHORIZATION;
            case 404:
            case 500:
                return FetchDataResult.FAILED_BY_SERVER;
            default:
                return FetchDataResult.FAILED_BY_UNEXPECTED_ERROR;
        }
    }

    public String getCityId() {
        return jsonResponse.get("Key").toString();
    }
}
