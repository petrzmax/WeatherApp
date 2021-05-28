package pl.arturpetrzak.view;

import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import pl.arturpetrzak.DailyForecastManager;
import pl.arturpetrzak.controller.AboutWindowController;
import pl.arturpetrzak.controller.BaseController;
import pl.arturpetrzak.controller.MainWindowController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewFactory {
    private List<Stage> activeStages;
    private DailyForecastManager dailyForecastManager;
    private HostServices hostServices;

    public ViewFactory(HostServices hostServices) {
        this.hostServices = hostServices;
        this.activeStages = new ArrayList<>();
    }

    public void showMainWindow() {
        BaseController controller = new MainWindowController(this, "MainWindow.fxml");
        initializeStage(controller);
    }

    public void showAboutWindow() {
        BaseController controller = new AboutWindowController(this, "AboutWindow.fxml");
        initializeStage(controller);
    }

    private void initializeStage(BaseController baseController) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(baseController.getFxmlName()));
        fxmlLoader.setController(baseController);
        Parent parent;

        try {
            parent = fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return;
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
    }

    public void closeStage(Stage stageToClose) {
        stageToClose.close();
        activeStages.remove(stageToClose);
    }

    public HostServices getHostServices() {
        return hostServices;
    }
}
