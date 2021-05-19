package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.Observer;
import pl.arturpetrzak.view.ViewFactory;

public class MainWindowController extends BaseController implements Observer {

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
    void checkGivenDataWeatherAction() {

    }

    @FXML
    void refreshAction() {

    }


    public MainWindowController(DailyForecastManager dailyForecastManager, ViewFactory viewFactory, String fxmlName) {
        super(dailyForecastManager, viewFactory, fxmlName);
        dailyForecastManager.addObserver(this);
        dailyForecastManager.getCurrentCityData();
    }


    @Override
    public void update() {
        currentCountryLabel.setText(dailyForecastManager.getCurrentCountryName());
        currentCityLabel.setText(dailyForecastManager.getCurrentCityName());
        currentLocalizationWeatherMessageLabel.setText(dailyForecastManager.getWeatherMessage());
    }
}
