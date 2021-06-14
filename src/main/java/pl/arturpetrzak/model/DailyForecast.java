package pl.arturpetrzak.model;

import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DailyForecast {

    private final long unixTime;
    private final String unit;
    private final String minimumTemperature;
    private final String maximumTemperature;
    private final JSONObject day;
    private final JSONObject night;

    public DailyForecast(JSONObject dailyForecast) { //consider using Gson or Jackson
        unixTime = dailyForecast.getLong("EpochDate");
        unit = dailyForecast.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
        minimumTemperature = String.valueOf(dailyForecast.getJSONObject("Temperature").getJSONObject("Minimum").getFloat("Value"));
        maximumTemperature = String.valueOf(dailyForecast.getJSONObject("Temperature").getJSONObject("Maximum").getFloat("Value"));
        day = dailyForecast.getJSONObject("Day");
        night = dailyForecast.getJSONObject("Night");
    }

    public String getDate() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(unixTime), ZoneId.systemDefault());
        return localDateTime.format(DateTimeFormatter.ofPattern("dd-MM"));
    }

    public String getUnit() {
        return unit;
    }

    public String getMinimumTemperature() {
        return minimumTemperature;
    }

    public String getMaximumTemperature() {
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
