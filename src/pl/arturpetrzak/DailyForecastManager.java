package pl.arturpetrzak;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.arturpetrzak.controller.services.FetchCityDataService;
import pl.arturpetrzak.controller.services.FetchCurrentLocalizationService;
import pl.arturpetrzak.controller.services.FetchWeatherService;
import pl.arturpetrzak.model.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastManager {
    private JSONObject headline;
    private List<DailyForecast> dailyForecasts = new ArrayList();
    private String currentCountryName = "";
    private String currentCityName = "";
    private String currentCityId = "";

    public void loadWeatherData (JSONObject weatherData) {
        headline = weatherData.getJSONObject("Headline");
        JSONArray jsonArray = weatherData.getJSONArray("DailyForecasts");

        for (Object object: jsonArray) {
            DailyForecast dailyForecast = new DailyForecast((JSONObject) object);
            dailyForecasts.add(dailyForecast);
        }
    }

    public void getCurrentCityData() {
        FetchCurrentLocalizationService fetchCurrentLocalizationService = new FetchCurrentLocalizationService();
        fetchCurrentLocalizationService.start();
        fetchCurrentLocalizationService.setOnSucceeded(event -> {
            currentCountryName = fetchCurrentLocalizationService.getCountryName();
            currentCityName = fetchCurrentLocalizationService.getCityName();
            getCurrentCityId();
        });
    }

    public void getCurrentCityId() {
        FetchCityDataService fetchCityDataService = new FetchCityDataService(currentCountryName, currentCityName);
        fetchCityDataService.start();
        fetchCityDataService.setOnSucceeded(event -> {
            currentCityId = fetchCityDataService.getCityId();
            getCurrentCityWeatherData();
        });
    }

    private void getCurrentCityWeatherData() {
        FetchWeatherService fetchWeatherService = new FetchWeatherService(currentCityId);
        fetchWeatherService.start();
        fetchWeatherService.setOnSucceeded(event -> {
            loadWeatherData(fetchWeatherService.getWeatherData());

        });
    }

    public String getCurrentCountryName() {
        return currentCountryName;
    }

    public String getCurrentCityName() {
        return currentCityName;
    }
}
