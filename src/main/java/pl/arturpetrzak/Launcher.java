package pl.arturpetrzak;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.arturpetrzak.view.ViewFactory;

public class Launcher extends Application {

    private DailyForecastManager dailyForecastManager;

    @Override
    public void start(Stage stage) {
        ViewFactory viewFactory = new ViewFactory(getHostServices());
        ViewFactory viewFactory = new ViewFactory(dailyForecastManager, getHostServices());
        viewFactory.showMainWindow();
    }

    public static void main(String[] args) {
        launch();
    }

}