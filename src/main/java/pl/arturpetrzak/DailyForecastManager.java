package pl.arturpetrzak;

import pl.arturpetrzak.controller.FetchDataResult;
import pl.arturpetrzak.controller.Location;
import pl.arturpetrzak.controller.persistence.Settings;
import pl.arturpetrzak.controller.services.FetchCityDataService;
import pl.arturpetrzak.controller.services.FetchCurrentLocalizationService;
import pl.arturpetrzak.controller.services.FetchWeatherService;
import pl.arturpetrzak.model.DailyForecast;
import pl.arturpetrzak.model.LocationForecast;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class DailyForecastManager implements Observable {

    private List<Observer> observers;
    private Map<Location, LocationForecast> locationForecasts;
    private boolean metric;
    private Languages language = Languages.ENGLISH;

    public DailyForecastManager(Settings settings) {
        observers = new ArrayList<>();
        locationForecasts = new EnumMap<>(Location.class);
        for (Location location : Location.values()) {
            LocationForecast locationForecast = new LocationForecast();
            locationForecasts.put(location, locationForecast);
        }
    }

    public void getCurrentLocalization(Location location) {
        pushMessage(Messages.FETCHING_LOCALIZATION);

        FetchCurrentLocalizationService fetchCurrentLocalizationService = new FetchCurrentLocalizationService();
        fetchCurrentLocalizationService.start();
        fetchCurrentLocalizationService.setOnSucceeded(event -> {
            try {
                fetchingResultHandler(fetchCurrentLocalizationService.getValue(), Messages.FETCHING_LOCALIZATION);
                locationForecasts.get(location).setCountry(fetchCurrentLocalizationService.getCountry());
                locationForecasts.get(location).setCity(fetchCurrentLocalizationService.getCity());
                getCityId(location);

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getCityId(Location location) {
        pushMessage(Messages.FETCHING_CITY_ID);

        FetchCityDataService fetchCityDataService = new FetchCityDataService(
                locationForecasts.get(location).getCountry(),
                locationForecasts.get(location).getCity()
        );

        fetchCityDataService.start();
        fetchCityDataService.setOnSucceeded(event -> {
            try {
                fetchingResultHandler(fetchCityDataService.getValue(), Messages.FETCHING_CITY_ID);
                locationForecasts.get(location).setCityId(fetchCityDataService.getCityId());
                getCityWeatherData(location);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getCityWeatherData(Location location) {
        pushMessage(Messages.FETCHING_WEATHER_DATA);

        FetchWeatherService fetchWeatherService = new FetchWeatherService(locationForecasts.get(location).getCityId(), metric, language);
        fetchWeatherService.start();
        fetchWeatherService.setOnSucceeded(event -> {
            try {
                fetchingResultHandler(fetchWeatherService.getValue(), Messages.FETCHING_WEATHER_DATA);
                locationForecasts.get(location).loadData(fetchWeatherService.getWeatherData());
                notifyObservers(
                        location,
                        locationForecasts.get(location).getCountry(),
                        locationForecasts.get(location).getCity(),
                        locationForecasts.get(location).getWeatherMessage()
                );
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void fetchingResultHandler(FetchDataResult fetchDataResult, String messageHeader) throws Exception {
        String message = messageHeader + ": ";

        switch (fetchDataResult) {
            case SUCCESS:
                message += Messages.SUCCESS;
                break;
            case RESPONSE_EMPTY:
                message += Messages.RESPONSE_EMPTY;
                break;
            case FAILED_BY_REQUEST_SYNTAX:
                message += Messages.REQUEST_SYNTAX_ERROR;
                break;
            case FAILED_BY_API_AUTHORIZATION:
                message += Messages.API_AUTHORIZATION_ERROR;
                break;
            case FAILED_BY_SERVER:
                message += Messages.SERVER_ERROR;
                break;
            case FAILED_BY_UNEXPECTED_ERROR:
                message += Messages.UNEXPECTED_ERROR;
                break;
            default:
                message += Messages.UNSUPPORTED_ERROR;
                break;
        }
        pushMessage(message);

        if (fetchDataResult != FetchDataResult.SUCCESS) {
            throw new Exception(message);
        }
    }

    public List<DailyForecast> getDailyForecasts(Location location) {
        return locationForecasts.get(location).getDailyForecasts();
    }

    public void setCountry(Location location, String country) {
        locationForecasts.get(location).setCountry(country);
    }

    public void setCity(Location location, String city) {
        locationForecasts.get(location).setCity(city);
    }

    public void setMetric(boolean metric) {
        this.metric = metric;
    }

    public boolean isMetric() {
        return metric;
    }

    public void setLanguage(Languages language) {
        this.language = language;
    }

    public Languages getLanguage() {
        return language;
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(Location location, String country, String city, String weatherMessage) {
        for (Observer observer : observers) {
            observer.update(location, country, city, weatherMessage);
        }
    }

    @Override
    public void pushMessage(String message) {
        for (Observer observer : observers) {
            observer.catchMessage(message);
        }
    }

}
