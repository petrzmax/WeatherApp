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
    void checkGivenDataWeatherAction() {
        dailyForecastManager.setCountry(Location.CHOSEN, countryTextField.getText());
        dailyForecastManager.setCity(Location.CHOSEN, cityTextField.getText());
        dailyForecastManager.getCityId(Location.CHOSEN);
    }

    @FXML
    void refreshAction() {

    }


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        dailyForecastManager.addObserver(this);
        dailyForecastManager.getCityData(Location.CURRENT);
    }


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
