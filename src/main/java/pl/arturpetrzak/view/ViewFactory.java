package pl.arturpetrzak.view;

import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.Languages;
import pl.arturpetrzak.controller.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {
    private final DailyForecastManager dailyForecastManager;
    private final HostServices hostServices;
    private boolean aboutWindowInitialized;
    private boolean settingsWindowInitialized;

    public ViewFactory(DailyForecastManager dailyForecastManager, HostServices hostServices) {
        this.dailyForecastManager = dailyForecastManager;
        this.hostServices = hostServices;
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(dailyForecastManager, this, "MainWindow.fxml");
        initializeStage(controller);
    }

    public void showSettingsWindow() {
        BaseController controller = new SettingsWindowController(this, "SettingsWindow.fxml");
        Stage stage = initializeStage(controller);

        stage.setOnHiding(event -> settingsWindowInitialized = false);
        settingsWindowInitialized = true;
    }

    public void showAboutWindow() {
        BaseController controller = new AboutWindowController(this, "AboutWindow.fxml");
        Stage stage = initializeStage(controller);

        stage.setOnHiding(event -> aboutWindowInitialized = false);
        aboutWindowInitialized = true;
    }

    private Stage initializeStage(BaseController baseController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;

        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UNIFIED);
        stage.setMinWidth(parent.minWidth(-1));
        stage.setMinHeight(parent.minHeight(-1));
        stage.show();

        return stage;
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
    }

    public HostServices getHostServices() {
        return hostServices;
    }

    public Languages getCurrentLanguage() {
        return dailyForecastManager.getLanguage();
    }

    public void setLanguage(Languages language) {
        dailyForecastManager.setLanguage(language);
    }

    public void setUsingMetricUnits(Boolean isUsingMetricUnits) {
        dailyForecastManager.setUsingMetricUnits(isUsingMetricUnits);
    }

    public boolean isUsingMetricUnits() {
        return dailyForecastManager.isUsingMetricUnits();
    }

    public boolean isAboutWindowInitialized() {
        return aboutWindowInitialized;
    }

    public boolean isSettingsWindowInitialized() {
        return settingsWindowInitialized;
    }

    public void refreshCurrentLocationData() {
        dailyForecastManager.getCurrentLocalization(Location.CURRENT);
    }
}
