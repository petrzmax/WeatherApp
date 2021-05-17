package pl.arturpetrzak.model;

import org.json.JSONObject;

public class DailyForecast {

    private int unixTime;
    private float minimumTemperature;
    private float maximumTemperature;
    private JSONObject day;
    private JSONObject night;

    public DailyForecast(JSONObject dailyForecast) {
        unixTime = dailyForecast.getInt("EpochDate");
        minimumTemperature = dailyForecast.getJSONObject("Temperature").getJSONObject("Minimum").getFloat("Value");
        maximumTemperature = dailyForecast.getJSONObject("Temperature").getJSONObject("Maximum").getFloat("Value");
        day = dailyForecast.getJSONObject("Day");
        night = dailyForecast.getJSONObject("Night");
    }

    public float getMinimumTemperature() {
        return minimumTemperature;
    }

    public float getMaximumTemperature() {
        return maximumTemperature;
    }
}
