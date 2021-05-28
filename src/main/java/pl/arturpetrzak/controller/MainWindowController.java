package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import pl.arturpetrzak.*;
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
    void refreshCurrentLocalizationDataAction() {
        dailyForecastManager.getCityWeatherData(Location.CURRENT);
    }

    @FXML
    void refreshChosenLocalizationDataAction() {
        if (validateUserInput()) {
            dailyForecastManager.setCountry(Location.CHOSEN, countryTextField.getText());
            dailyForecastManager.setCity(Location.CHOSEN, cityTextField.getText());
            dailyForecastManager.getCityId(Location.CHOSEN);
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

    @FXML
    void setMetricUnitsAction() {
        dailyForecastManager.setMetric(true);
    }

    @FXML
    void setImperialUnitsAction() {
        dailyForecastManager.setMetric(false);
    }

    @FXML
    void setEnglishLanguageAction() {
        dailyForecastManager.setLanguage(Languages.ENGLISH);
    }

    @FXML
    void setPolishLanguageAction() {
        dailyForecastManager.setLanguage(Languages.POLISH);
    }

    @FXML
    void setGermanLanguageAction() {
        dailyForecastManager.setLanguage(Languages.GERMAN);
    }

    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        dailyForecastManager.addObserver(this);
        dailyForecastManager.getCurrentLocalization(Location.CURRENT);
        dailyForecastManager.setMetric(true);
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
    public void catchMessage(String message) {
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

        Pattern digit = Pattern.compile("[0-9]");
        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");

        Matcher hasDigit;
        Matcher hasSpecial;

        hasDigit = digit.matcher(countryName);
        if (hasDigit.find()) {
            catchMessage(Messages.COUNTRY_NAME_NO_NUMBERS);
            return false;
        }
        hasSpecial = special.matcher(countryName);
        if (hasSpecial.find()) {
            catchMessage(Messages.COUNTRY_NAME_NO_SPECIAL_CHARACTERS);
            return false;
        }

        hasDigit = digit.matcher(cityName);
        if (hasDigit.find()) {
            catchMessage(Messages.CITY_NAME_NO_NUMBERS);
            return false;
        }
        hasSpecial = special.matcher(cityName);
        if (hasSpecial.find()) {
            catchMessage(Messages.CITY_NAME_NO_SPECIAL_CHARACTERS);
            return false;
        }

        if (countryName.length() > Config.getTextFieldCapacity()) {
            catchMessage(Messages.COUNTRY_NAME_TOO_LONG);
            return false;
        }

        if (cityName.length() > Config.getTextFieldCapacity()) {
            catchMessage(Messages.CITY_NAME_TOO_LONG);
            return false;
        }

        if (cityName == "") {
            catchMessage(Messages.NO_CITY_NAME);
            return false;
        }

        return true;
    }
}
