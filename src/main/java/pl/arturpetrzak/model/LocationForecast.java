package pl.arturpetrzak.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class LocationForecast {
    private String weatherMessage;
    private List<DailyForecast> dailyForecasts;
    private String country = "";
    private String city = "";
    private String cityId = "";

    public void loadData(JSONObject weatherData) {
        weatherMessage = weatherData.getJSONObject("Headline").getString("Text");
        dailyForecasts = new ArrayList();
        JSONArray jsonArray = weatherData.getJSONArray("DailyForecasts");

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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }
}
