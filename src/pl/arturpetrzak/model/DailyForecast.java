package pl.arturpetrzak.model;

import org.json.JSONObject;

public class DailyForecast {

    private int unixTime;
    private String unit;
    private float minimumTemperature;
    private float maximumTemperature;
    private JSONObject day;
    private JSONObject night;

    public DailyForecast(JSONObject dailyForecast) {
        unixTime = dailyForecast.getInt("EpochDate");
        unit = dailyForecast.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
        minimumTemperature = dailyForecast.getJSONObject("Temperature").getJSONObject("Minimum").getFloat("Value");
        maximumTemperature = dailyForecast.getJSONObject("Temperature").getJSONObject("Maximum").getFloat("Value");
        day = dailyForecast.getJSONObject("Day");
        night = dailyForecast.getJSONObject("Night");
    }

    public String getUnit() {
        return unit;
    }

    public float getMinimumTemperature() {
        return minimumTemperature;
    }

    public float getMaximumTemperature() {
        return maximumTemperature;
    }

    public int getDayIconNumber() {
        return day.getInt("Icon");
    }

    public String getDayWeatherDescription() {
        return day.getString("IconPhrase");
    }

    public String getNightWeatherDescription() {
        return night.getString("IconPhrase");
    }

    public int getNightIconNumber() {
        return night.getInt("Icon");
    }
}
