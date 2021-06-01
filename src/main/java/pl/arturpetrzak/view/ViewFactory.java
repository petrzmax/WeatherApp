package pl.arturpetrzak.view;

import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.Languages;
import pl.arturpetrzak.controller.AboutWindowController;
import pl.arturpetrzak.controller.BaseController;
import pl.arturpetrzak.controller.MainWindowController;
import pl.arturpetrzak.controller.SettingsWindowController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {
    private List<Stage> activeStages;
    private DailyForecastManager dailyForecastManager;
    private final HostServices hostServices;
    private boolean aboutWindowInitialized;
    private boolean settingsWindowInitialized;

    public ViewFactory(DailyForecastManager dailyForecastManager, HostServices hostServices) {
        this.dailyForecastManager = dailyForecastManager;
        this.hostServices = hostServices;
        this.activeStages = new ArrayList<>();
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

        activeStages.add(stage);
        return stage;
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
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

    public void setMetric(Boolean metric) {
        dailyForecastManager.setMetric(metric);
    }

    public boolean isMetric() {
        return dailyForecastManager.isMetric();
    }

    public boolean isAboutWindowInitialized() {
        return aboutWindowInitialized;
    }

    public boolean isSettingsWindowInitialized() {
        return settingsWindowInitialized;
    }
}
