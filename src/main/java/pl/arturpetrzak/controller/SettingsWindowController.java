package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import pl.arturpetrzak.Languages;
import pl.arturpetrzak.view.ViewFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingsWindowController extends BaseController implements Initializable {


    @FXML
    private ChoiceBox<String> languageChoiceBox;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpLanguageChoiceBox();
    }

    private void setUpLanguageChoiceBox() {
        List<String> enumNames = Stream.of(Languages.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        for(int i = 0; i < enumNames.size(); i++) {
            enumNames.set(i, stringFormat(enumNames.get(i)));
        }

        languageChoiceBox.getItems().setAll(enumNames);
        languageChoiceBox.setValue(stringFormat(
                viewFactory.getCurrentLanguage().name()
        ));
    }

    private String stringFormat(String string) {
        return string.substring(0,1) +string.substring(1).toLowerCase();
    }
}


