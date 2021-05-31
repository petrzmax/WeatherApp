package pl.arturpetrzak;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.arturpetrzak.controller.persistence.PersistenceAccess;
import pl.arturpetrzak.controller.persistence.Settings;
import pl.arturpetrzak.view.ViewFactory;

public class Launcher extends Application {

    private DailyForecastManager dailyForecastManager;
    private PersistenceAccess persistenceAccess = new PersistenceAccess();

    @Override
    public void start(Stage stage) {
        dailyForecastManager = new DailyForecastManager(persistenceAccess.loadFromPersistence());

        ViewFactory viewFactory = new ViewFactory(dailyForecastManager, getHostServices());
        viewFactory.showMainWindow();
    }

    @Override
    public void stop() throws Exception {
        Settings settings = new Settings();
        settings.setLanguage(dailyForecastManager.getLanguage());
        settings.setMetric(dailyForecastManager.isMetric());

        persistenceAccess.saveToPersistence(settings);
    }


    public static void main(String[] args) {
        launch();
    }

}