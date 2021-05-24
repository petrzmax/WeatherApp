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
    private HBox currentCityWeatherBox;

    @FXML
    void checkGivenDataWeatherAction() {

    }

    @FXML
    void refreshAction() {

    }


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        currentCityForecastManager.addObserver(this);
        currentCityForecastManager.getCityData();
    }


    @Override
    public void update() {
        currentCountryLabel.setText(currentCityForecastManager.getCountry());
        currentCityLabel.setText(currentCityForecastManager.getCity());
        currentLocalizationWeatherMessageLabel.setText(currentCityForecastManager.getWeatherMessage());

        populateWeatherBox(currentCityWeatherBox, currentCityForecastManager.getDailyForecasts());
    }

    private void populateWeatherBox(HBox weatherBox, List<DailyForecast> dailyForecasts) {
        DailyForecastRepresentation dailyForecastRepresentation = new DailyForecastRepresentation();
        WeatherIconResolver weatherIconResolver = new WeatherIconResolver();

        for(DailyForecast dailyForecast: dailyForecasts) {
            weatherBox.getChildren().add(
                    dailyForecastRepresentation.getDailyForecastRepresentation(
                            dailyForecast,
                            weatherIconResolver.getIconForWeather(dailyForecast.getDayIconNumber(), 120),
                            weatherIconResolver.getIconForWeather(dailyForecast.getNightIconNumber(), 120))
            );
        }

    }
}
