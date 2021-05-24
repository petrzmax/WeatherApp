package pl.arturpetrzak;

import org.json.JSONArray;
import org.json.JSONObject;
import pl.arturpetrzak.controller.services.FetchCityDataService;
import pl.arturpetrzak.controller.services.FetchCurrentLocalizationService;
import pl.arturpetrzak.controller.services.FetchWeatherService;
import pl.arturpetrzak.model.DailyForecast;

import java.util.ArrayList;
import java.util.List;

public class DailyForecastManager implements Observable{
    private List<Observer> observers;

    private JSONObject headline;
    private List<DailyForecast> dailyForecasts = new ArrayList();
    private String country = "";
    private String city = "";
    private String cityId = "";

    public DailyForecastManager() {
        observers = new ArrayList<>();
    }

    public void loadWeatherData (JSONObject weatherData) {
        headline = weatherData.getJSONObject("Headline");
        JSONArray jsonArray = weatherData.getJSONArray("DailyForecasts");

        for (Object object: jsonArray) {
            DailyForecast dailyForecast = new DailyForecast((JSONObject) object);
            dailyForecasts.add(dailyForecast);
        }
    }

    public void getCityData() {
        FetchCurrentLocalizationService fetchCurrentLocalizationService = new FetchCurrentLocalizationService();
        fetchCurrentLocalizationService.start();
        fetchCurrentLocalizationService.setOnSucceeded(event -> {
            country = fetchCurrentLocalizationService.getCountry();
            city = fetchCurrentLocalizationService.getCity();
            getCityId();
        });
    }

    public void getCityId() {
        FetchCityDataService fetchCityDataService = new FetchCityDataService(country, city);
        fetchCityDataService.start();
        fetchCityDataService.setOnSucceeded(event -> {
            cityId = fetchCityDataService.getCityId();
            getCityWeatherData();
        });
    }

    private void getCityWeatherData() {
        FetchWeatherService fetchWeatherService = new FetchWeatherService(cityId);
        fetchWeatherService.start();
        fetchWeatherService.setOnSucceeded(event -> {
            loadWeatherData(fetchWeatherService.getWeatherData());
            notifyObservers();
        });
    }

    public List<DailyForecast> getDailyForecasts() {
        return dailyForecasts;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getWeatherMessage() {
        return headline.getString("Text");
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        observers.forEach(Observer:: update);
    }
}
