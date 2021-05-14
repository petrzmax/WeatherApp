package pl.arturpetrzak.controller.services;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONObject;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.controller.FetchDataResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchWeatherService extends Service<FetchDataResult> {
    private final String API_KEY_PREFIX = "apikey=";

    private String cityId;

    private OkHttpClient client;
    private JSONObject jsonResponse;

    public FetchWeatherService(String cityId) {
        this.cityId = cityId;
        client = new OkHttpClient();
    }

    @Override
    protected Task createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                return fetchWeatherData();
            }
        };
    }

    private FetchDataResult fetchWeatherData() {
        Response response = null;
        try {
            URL url = new URL(Config.getDailyWeatherForecastApiUrl() + cityId + "?" + API_KEY_PREFIX + Config.getApiKey());
            System.out.println(url.toString());
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if(response.code() != 200) {
                throw new IOException("HttpRequestCode: " + response.code());
            }
            jsonResponse = new JSONObject(response.body().string());

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
                return FetchDataResult.FAILED_BY_API_AUTHORIZATION;
            case 404:
            case 500:
                return FetchDataResult.FAILED_BY_SERVER;
            default:
                return FetchDataResult.FAILED_BY_UNEXPECTED_ERROR;
        }
    }

    public JSONObject getWeatherData() {
        return jsonResponse;
    }
}
