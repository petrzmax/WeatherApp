package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.Languages;
import pl.arturpetrzak.Messages;
import pl.arturpetrzak.view.ViewFactory;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SettingsWindowController extends BaseController implements Initializable {


    @FXML
    private ChoiceBox<String> languageChoiceBox;

    @FXML
    private CheckBox metricCheckBox;

    @FXML
    private TextField ipStackApiTextField;

    @FXML
    private TextField accuWeatherApiTextField;

    @FXML
    void cancelSettingsAction() {
        Stage stage = (Stage) languageChoiceBox.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    @FXML
    void saveSettingsAction() {
        String choiceBoxValue = languageChoiceBox.getValue();
        Languages language = Languages.valueOf(choiceBoxValue.toUpperCase());

        viewFactory.setLanguage(language);
        viewFactory.setMetric(metricCheckBox.isSelected());

        Config.setIpstackApiKey(ipStackApiTextField.getText());
        Config.setAccuweatherApiKey(accuWeatherApiTextField.getText());

        Stage stage = (Stage) languageChoiceBox.getScene().getWindow();
        viewFactory.closeStage(stage);

    private boolean validateUserInput() {
        String ipStackApi = ipStackApiTextField.getText();
        String accuWeatherApi = accuWeatherApiTextField.getText();

        Pattern special = Pattern.compile("[!@#$%&*()_+=|<>?{}\\[\\]~-]");
        Matcher hasSpecial;

        // IpStack
        hasSpecial = special.matcher(ipStackApi);
        if (hasSpecial.find()) {
            pushAlert("IpStack: " + Messages.API_KEY_NO_SPECIAL_CHARACTERS);
            return false;
        }

        if(ipStackApi.length() > 50) {
            pushAlert("IpStack: " + Messages.API_KEY_TOO_LONG);
            return false;
        }

        // AccuWeather
        hasSpecial = special.matcher(accuWeatherApi);
        if (hasSpecial.find()) {
            pushAlert("AccuWeather: " + Messages.API_KEY_NO_SPECIAL_CHARACTERS);
            return false;
        }

        if(accuWeatherApi.length() > 50) {
            pushAlert("AccuWeather: " + Messages.API_KEY_TOO_LONG);
            return false;
        }

        return true;
    }

    public SettingsWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setUpLanguageChoiceBox();
        metricCheckBox.setSelected(viewFactory.isMetric());
        ipStackApiTextField.setText(Config.getIpstackApiKey());
        accuWeatherApiTextField.setText(Config.getAccuWeatherApiKey());
    }

    private void setUpLanguageChoiceBox() {
        List<String> enumNames = Stream.of(Languages.values())
                .map(Enum::name)
                .collect(Collectors.toList());

        for (int i = 0; i < enumNames.size(); i++) {
            enumNames.set(i, stringFormat(enumNames.get(i)));
        }

        languageChoiceBox.getItems().setAll(enumNames);
        languageChoiceBox.setValue(stringFormat(
                viewFactory.getCurrentLanguage().name()
        ));
    }

    private String stringFormat(String string) {
        return string.charAt(0) + string.substring(1).toLowerCase();
    }

    private void pushAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText(message);

        alert.showAndWait();
    }
}


