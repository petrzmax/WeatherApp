package pl.arturpetrzak;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.arturpetrzak.controller.persistence.PersistenceAccess;
import pl.arturpetrzak.controller.persistence.Settings;
import pl.arturpetrzak.view.ViewFactory;

import java.io.File;
import java.util.Optional;

public class Launcher extends Application {

    private DailyForecastManager dailyForecastManager;
    private final PersistenceAccess persistenceAccess = new PersistenceAccess(
            System.getProperty("user.home") + File.separator + Config.getAppName() + "Settings.ser"
    );

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) {
        Optional<Settings> settings = persistenceAccess.loadFromPersistence();

        if (settings.isPresent()) {
            dailyForecastManager = new DailyForecastManager(settings.get());
        } else {
            dailyForecastManager = new DailyForecastManager();
        }

        ViewFactory viewFactory = new ViewFactory(dailyForecastManager, getHostServices());
        viewFactory.showMainWindow();
    }

    @Override
    public void stop() {
        Settings settings = new Settings();

        settings.setIpstackApiKey(Config.getIpstackApiKey());
        settings.setAccuweatherApiKey(Config.getAccuWeatherApiKey());

        settings.setLanguage(dailyForecastManager.getLanguage());
        settings.setUsingMetricUnits(dailyForecastManager.isUsingMetricUnits());

        persistenceAccess.saveToPersistence(settings);
    }

}