package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import pl.arturpetrzak.view.ViewFactory;

public class SettingsWindowController extends BaseController{


    @FXML
    private ChoiceBox<?> languageChoiceBox;

    @FXML
    void cancelSettingsAction() {

    }

    @FXML
    void saveSettingsAction() {

    }


    public SettingsWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }
}
