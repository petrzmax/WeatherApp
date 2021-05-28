package pl.arturpetrzak.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.view.ViewFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class AboutWindowController extends BaseController implements Initializable {

    @FXML
    private Label nameLabel;

    @FXML
    private Label nameVersionLabel;

    @FXML
    private Hyperlink myPageHyperlink;

    @FXML
    void openHyperlinkAction() {
        viewFactory.getHostServices().showDocument("https://" + myPageHyperlink.getText());
    }

    @FXML
    void closeWindowAction() {
        Stage stage = (Stage) nameVersionLabel.getScene().getWindow();
        viewFactory.closeStage(stage);
    }

    public AboutWindowController(ViewFactory viewFactory, String fxmlName) {
        super(viewFactory, fxmlName);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        String string = nameVersionLabel.getText().replace("<appName>", Config.getAppName()).replace("<version>", Config.getVersion());
        nameVersionLabel.setText(string);
        string = nameLabel.getText().replace("<appName>", Config.getAppName());
        nameLabel.setText(string);
    }
}
