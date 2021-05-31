package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import pl.arturpetrzak.view.ViewFactory;

public class SettingsWindowController extends BaseController{


    @FXML
    private ChoiceBox<?> languageChoiceBox;

    @FXML
    void cancelSettingsAction() {
        Stage stage = (Stage) languageChoiceBox.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @FXML
    void saveSettingsAction() {
        //save
        Stage stage = (Stage) languageChoiceBox.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    public SettingsWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }
}
