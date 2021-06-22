package pl.arturpetrzak;

import okhttp3.OkHttpClient;
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

    final private List<Observer> observers;
    final private Map<Location, LocationForecast> locationForecasts;
    private boolean usingMetricUnits;
    private Languages language = Languages.ENGLISH;

    private final OkHttpClient okHttpClient;
    private final FetchCurrentLocalizationService fetchCurrentLocalizationService;
    private final FetchCityDataService fetchCityDataService;
    private final FetchWeatherService fetchWeatherService;


    public DailyForecastManager() {
        observers = new ArrayList<>();
        okHttpClient = new OkHttpClient();
        fetchCurrentLocalizationService = new FetchCurrentLocalizationService(okHttpClient);
        fetchCityDataService = new FetchCityDataService(okHttpClient);
        fetchWeatherService = new FetchWeatherService(okHttpClient);
        locationForecasts = new EnumMap<>(Location.class);
    }

    public DailyForecastManager(Settings settings) {
        this();

        usingMetricUnits = settings.isUsingMetricUnits();
        language = settings.getLanguage();

        Config.setIpstackApiKey(settings.getIpstackApiKey());
        Config.setAccuweatherApiKey(settings.getAccuweatherApiKey());
    }

    public void getCurrentLocalization(Location location) {
        pushMessage(Messages.FETCHING_LOCALIZATION);

        fetchCurrentLocalizationService.restart();
        fetchCurrentLocalizationService.setOnSucceeded(event -> {
            try {
                fetchingResultHandler(fetchCurrentLocalizationService.getValue(), Messages.FETCHING_LOCALIZATION);
                getCityId(location, fetchCurrentLocalizationService.getCountry(), fetchCurrentLocalizationService.getCity());

            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getCityId(Location location, String country, String city) {
        pushMessage(Messages.FETCHING_CITY_ID);

        fetchCityDataService.setCountry(country);
        fetchCityDataService.setCity(city);

        fetchCityDataService.restart();
        fetchCityDataService.setOnSucceeded(event -> {
            try {
                fetchingResultHandler(fetchCityDataService.getValue(), Messages.FETCHING_CITY_ID);

                getCityWeatherData(location, country, city, fetchCityDataService.getCityId());
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void getCityWeatherData(Location location, String country, String city, String cityId) {
        pushMessage(Messages.FETCHING_WEATHER_DATA);

        fetchWeatherService.setCityId(cityId);
        fetchWeatherService.setUsingMetricUnits(usingMetricUnits);
        fetchWeatherService.setLanguage(language);

        fetchWeatherService.restart();
        fetchWeatherService.setOnSucceeded(event -> {
            try {
                fetchingResultHandler(fetchWeatherService.getValue(), Messages.FETCHING_WEATHER_DATA);
                locationForecasts.put(location, new LocationForecast(fetchWeatherService.getWeatherData()));
                notifyObservers(location, country, city,
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

    public void setUsingMetricUnits(boolean usingMetricUnits) {
        this.usingMetricUnits = usingMetricUnits;
    }

    public boolean isUsingMetricUnits() {
        return usingMetricUnits;
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
            observer.displayMessage(message);
        }
    }

}
