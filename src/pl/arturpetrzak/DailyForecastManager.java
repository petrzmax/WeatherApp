package pl.arturpetrzak;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.arturpetrzak.model.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastManager {
    private JSONObject headline;
    private List<DailyForecast> dailyForecasts = new ArrayList();

    public void loadWeatherData (JSONObject weatherData) {
        headline = weatherData.getJSONObject("Headline");
        JSONArray jsonArray = weatherData.getJSONArray("DailyForecasts");

        for (Object object: jsonArray) {
            DailyForecast dailyForecast = new DailyForecast((JSONObject) object);
            dailyForecasts.add(dailyForecast);
        }
    }
}
