package pl.arturpetrzak.controller;

import pl.arturpetrzak.view.ViewFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class MainWindowController extends BaseController{


    @FXML
    private TextField countryTextField;

    @FXML
    private TextField cityTextField;


    public MainWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }
}
