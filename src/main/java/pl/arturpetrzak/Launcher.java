package pl.arturpetrzak;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;
import pl.arturpetrzak.view.ViewFactory;

public class Launcher extends Application {

    @Override
    public void start(Stage stage) {

        viewFactory.showMainWindow();
        ViewFactory viewFactory = new ViewFactory(getHostServices());
    }

    public static void main(String[] args) { launch(); }

}