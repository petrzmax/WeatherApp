package pl.arturpetrzak.controller.services;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import org.json.JSONObject;
import pl.arturpetrzak.controller.FetchDataResult;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public abstract class BaseApiService extends Service<FetchDataResult> {
    protected final String API_KEY_PREFIX = "apikey=";
    protected final String CITY_PREFIX = "q=";

    protected String url;
    protected JSONObject jsonResponse;
    protected OkHttpClient client;

    public BaseApiService() {
        client = new OkHttpClient();
    }

    @Override
    protected Task<FetchDataResult> createTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                return fetchData();
            }
        };
    }

    protected FetchDataResult fetchData() {
        Response response = null;
        try {
            URL url = new URL(this.url);
            Request request = new Request.Builder()
                    .url(url)
                    .build();

            response = client.newCall(request).execute();

            if (response.code() != 200) {
                throw new IOException("HttpRequestCode: " + response.code());
            }

            jsonResponse = parseResponse(response.body().string());

            if (jsonResponse == null) {
                return FetchDataResult.RESPONSE_EMPTY;
            }

        } catch (MalformedURLException e) {
            e.printStackTrace();
            return FetchDataResult.FAILED_BY_REQUEST_SYNTAX;
        } catch (IOException e) {
            e.printStackTrace();
            return getFetchDataResultByResponseCode(response.code());
        }
        return FetchDataResult.SUCCESS;
    }

    protected FetchDataResult getFetchDataResultByResponseCode(int responseCode) {
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

    protected abstract JSONObject parseResponse(String response);
}
