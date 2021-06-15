package pl.arturpetrzak.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationForecast {
    private final String weatherMessage;
    private final String country;
    private final String city;
    private final List<DailyForecast> dailyForecasts;

    public LocationForecast(String country, String city, JSONObject weatherData) {
        weatherMessage = weatherData.getJSONObject("Headline").getString("Text");
        this.country = country;
        this.city = city;

        JSONArray jsonArray = weatherData.getJSONArray("DailyForecasts");

        dailyForecasts = new ArrayList<>();
        for (Object object : jsonArray) {
            DailyForecast dailyForecast = new DailyForecast((JSONObject) object);
            dailyForecasts.add(dailyForecast);
        }
    }

    public String getWeatherMessage() {
        return weatherMessage;
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

}
