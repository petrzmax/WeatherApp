package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.Messages;
import pl.arturpetrzak.Observer;
import pl.arturpetrzak.model.DailyForecast;
import pl.arturpetrzak.view.DailyForecastRepresentation;
import pl.arturpetrzak.view.ViewFactory;
import pl.arturpetrzak.view.WeatherIconResolver;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainWindowController extends BaseController implements Observer, Initializable {

    private final DailyForecastManager dailyForecastManager;

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

    private static final Pattern digits = Pattern.compile("[0-9]");

    public MainWindowController(DailyForecastManager dailyForecastManager, ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
        this.dailyForecastManager = dailyForecastManager;
    }

    @FXML
    void refreshLocalizationAction() {
        dailyForecastManager.getCurrentLocalization(Location.CURRENT);
    }

    @FXML
    void refreshCurrentLocalizationDataAction() {
        dailyForecastManager.getCurrentLocalization(Location.CURRENT);
    }

    @FXML
    void refreshChosenLocalizationDataAction() {
        if (validateUserInput()) {
            dailyForecastManager.getCityId(Location.CHOSEN, countryTextField.getText(), cityTextField.getText());
        }
    }

    @FXML
    void showSettingsWindowAction() {
        if (!viewFactory.isSettingsWindowInitialized()) {
            viewFactory.showSettingsWindow();
        }
    }

    @FXML
    void openAboutWindowAction() {
        if (!viewFactory.isAboutWindowInitialized()) {
            viewFactory.showAboutWindow();
        }
    }

    @FXML
    void closeAction() {
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dailyForecastManager.addObserver(this);
        dailyForecastManager.getCurrentLocalization(Location.CURRENT);
    }

    @Override
    public void update(Location location, String country, String city, String weatherMessage) {
        if (location == Location.CURRENT) {
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
    public void displayMessage(String message) {
        messageLabel.setText(message);
    }

    private void populateWeatherBox(HBox weatherBox, List<DailyForecast> dailyForecasts) {
        DailyForecastRepresentation dailyForecastRepresentation = new DailyForecastRepresentation();
        WeatherIconResolver weatherIconResolver = new WeatherIconResolver();
        weatherBox.getChildren().clear();

        for (DailyForecast dailyForecast : dailyForecasts) {
            weatherBox.getChildren().add(
                    dailyForecastRepresentation.getDailyForecastRepresentation(
                            dailyForecast,
                            weatherIconResolver.getIconForWeather(dailyForecast.getDayIconNumber()),
                            weatherIconResolver.getIconForWeather(dailyForecast.getNightIconNumber()))
            );
        }
        Stage stage = (Stage) messageLabel.getScene().getWindow();
        stage.sizeToScene();
    }

    private boolean validateUserInput() {
        String countryName = countryTextField.getText();
        String cityName = cityTextField.getText();

        Matcher hasDigit;
        Matcher hasSpecial;

        hasDigit = digits.matcher(countryName);
        if (hasDigit.find()) {
            displayMessage(Messages.COUNTRY_NAME_NO_NUMBERS);
            return false;
        }
        hasSpecial = specialCharactersPattern.matcher(countryName);
        if (hasSpecial.find()) {
            displayMessage(Messages.COUNTRY_NAME_NO_SPECIAL_CHARACTERS);
            return false;
        }

        hasDigit = digits.matcher(cityName);
        if (hasDigit.find()) {
            displayMessage(Messages.CITY_NAME_NO_NUMBERS);
            return false;
        }
        hasSpecial = specialCharactersPattern.matcher(cityName);
        if (hasSpecial.find()) {
            displayMessage(Messages.CITY_NAME_NO_SPECIAL_CHARACTERS);
            return false;
        }

        if (countryName.length() > Config.getTextFieldCapacity()) {
            displayMessage(Messages.COUNTRY_NAME_TOO_LONG);
            return false;
        }

        if (cityName.length() > Config.getTextFieldCapacity()) {
            displayMessage(Messages.CITY_NAME_TOO_LONG);
            return false;
        }

        if (cityName.equals("")) {
            displayMessage(Messages.NO_CITY_NAME);
            return false;
        }

        return true;
    }
}
