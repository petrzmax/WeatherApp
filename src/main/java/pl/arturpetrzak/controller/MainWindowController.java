package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.Observer;
import pl.arturpetrzak.model.DailyForecast;
import pl.arturpetrzak.view.DailyForecastRepresentation;
import pl.arturpetrzak.view.ViewFactory;
import pl.arturpetrzak.view.WeatherIconResolver;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MainWindowController extends BaseController implements Observer, Initializable {

    DailyForecastManager dailyForecastManager = new DailyForecastManager();

    @FXML
    private Label currentCountryLabel;

    @FXML
    private Label currentCityLabel;

    @FXML
    private Label currentLocalizationWeatherMessageLabel;

    @FXML
    private HBox currentCityWeatherBox;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    private Label chosenLocalizationWeatherMessageLabel;

    @FXML
    private HBox chosenCityWeatherBox;

    @FXML
    private Label messageLabel;

    @FXML
    void checkGivenDataWeatherAction() {
        dailyForecastManager.setCountry(Location.CHOSEN, countryTextField.getText());
        dailyForecastManager.setCity(Location.CHOSEN, cityTextField.getText());
        dailyForecastManager.getCityId(Location.CHOSEN);
    }

    @FXML
    void refreshAction() {

    }

    @FXML
    void closeAction() {

    }

    @FXML
    void setMetricUnitsAction() {
        dailyForecastManager.setMetric(true);
    }

    @FXML
    void setImperialUnitsAction() {
        dailyForecastManager.setMetric(false);
    }

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dailyForecastManager.addObserver(this);
        dailyForecastManager.getCityData(Location.CURRENT);
        dailyForecastManager.setMetric(true);
    }

    @Override
    public void update(Location location, String country, String city, String weatherMessage) {

        if(location == Location.CURRENT) {
            currentCountryLabel.setText(country);
            currentCityLabel.setText(city);
            currentLocalizationWeatherMessageLabel.setText(weatherMessage);
            populateWeatherBox(currentCityWeatherBox, dailyForecastManager.getDailyForecasts(location));

        } else if (location == Location.CHOSEN) {
            chosenLocalizationWeatherMessageLabel.setText(weatherMessage);
            populateWeatherBox(chosenCityWeatherBox, dailyForecastManager.getDailyForecasts(location));
        }
    }

    @Override
    public void catchMessage(String message) {
        messageLabel.setText(message);
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
