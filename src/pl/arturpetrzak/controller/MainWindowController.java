package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.Observer;
import pl.arturpetrzak.model.DailyForecast;
import pl.arturpetrzak.view.DailyForecastRepresentation;
import pl.arturpetrzak.view.ViewFactory;
import pl.arturpetrzak.view.WeatherIconResolver;

import java.util.List;

public class MainWindowController extends BaseController implements Observer {

    DailyForecastManager currentCityForecastManager = new DailyForecastManager();
    DailyForecastManager chosenCityForecastManager = new DailyForecastManager();

    @FXML
    private Label currentCountryLabel;

    @FXML
    private Label currentCityLabel;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private Label currentLocalizationWeatherMessageLabel;

    @FXML
    private HBox currentCityWeatherHBox;

    @FXML
    void checkGivenDataWeatherAction() {

    }

    @FXML
    void refreshAction() {

    }


        dailyForecastManager.addObserver(this);
        dailyForecastManager.getCurrentCityData();
    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }


    @Override
    public void update() {
        currentCountryLabel.setText(dailyForecastManager.getCurrentCountryName());
        currentCityLabel.setText(dailyForecastManager.getCurrentCityName());
        currentLocalizationWeatherMessageLabel.setText(dailyForecastManager.getWeatherMessage());

        DailyForecastRepresentation dailyForecastRepresentation = new DailyForecastRepresentation();
        WeatherIconResolver weatherIconResolver = new WeatherIconResolver();

        List<DailyForecast> dailyForecasts = dailyForecastManager.getDailyForecasts();

        for(DailyForecast dailyForecast: dailyForecasts) {
            currentCityWeatherHBox.getChildren().add(
                dailyForecastRepresentation.getDailyForecastRepresentation(
                        dailyForecast,
                        weatherIconResolver.getIconForWeather(dailyForecast.getDayIconNumber(), 120),
                        weatherIconResolver.getIconForWeather(dailyForecast.getNightIconNumber(), 120))
            );
        }


    }
}
