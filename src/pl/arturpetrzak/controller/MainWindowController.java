package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import pl.arturpetrzak.view.ViewFactory;

public class MainWindowController extends BaseController{

    @FXML
    private Label currentCountryLabel;

    @FXML
    private Label currentCityLabel;

    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;

    @FXML
    void refreshAction() {
        
    }


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

}
