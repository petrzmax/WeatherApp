package pl.arturpetrzak.controller;

import pl.arturpetrzak.controller.services.FetchCityDataService;
import pl.arturpetrzak.controller.services.FetchWeatherService;
import pl.arturpetrzak.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainWindowController extends BaseController{


    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    void testAction() {
        FetchCityDataService fetchCityDataService = new FetchCityDataService("Warszawa");

        fetchCityDataService.start();
        fetchCityDataService.setOnSucceeded(event -> {
            FetchDataResult fetchDataResult = fetchCityDataService.getValue();

            getCityData(fetchCityDataService.getCityId());
        });


    }


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    void getCityData(String cityId) {
        FetchWeatherService fetchWeatherService = new FetchWeatherService(cityId);

        fetchWeatherService.start();

        fetchWeatherService.setOnSucceeded(event -> {
            FetchDataResult fetchDataResult = fetchWeatherService.getValue();

            System.out.println(fetchWeatherService.getWeatherData());
        });
    }
}
