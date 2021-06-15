package pl.arturpetrzak.model;

import org.json.JSONObject;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DailyForecast {

    private final long epochDate;
    private final String unit;
    private final String minimumTemperature;
    private final String maximumTemperature;
    private final String dayIconPhrase;
    private final String nightIconPhrase;
    private final int dayIconNumber;
    private final int nightIconNumber;

    public DailyForecast(JSONObject dailyForecast) {

        epochDate = dailyForecast.getLong("EpochDate");
        unit = dailyForecast.getJSONObject("Temperature").getJSONObject("Minimum").getString("Unit");
        minimumTemperature = String.valueOf(dailyForecast.getJSONObject("Temperature").getJSONObject("Minimum").getFloat("Value"));
        maximumTemperature = String.valueOf(dailyForecast.getJSONObject("Temperature").getJSONObject("Maximum").getFloat("Value"));
        dayIconPhrase = dailyForecast.getJSONObject("Day").getString("IconPhrase");
        nightIconPhrase = dailyForecast.getJSONObject("Night").getString("IconPhrase");
        dayIconNumber = dailyForecast.getJSONObject("Day").getInt("Icon");
        nightIconNumber = dailyForecast.getJSONObject("Night").getInt("Icon");
    }

    public String getDate() {
        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(epochDate), ZoneId.systemDefault());
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

    public String getDayWeatherDescription() {
        return dayIconPhrase;
    }

    public String getNightWeatherDescription() {
        return nightIconPhrase;
    }

    public int getDayIconNumber() {
        return dayIconNumber;
    }

    public int getNightIconNumber() {
        return nightIconNumber;
    }
}
