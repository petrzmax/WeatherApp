package pl.arturpetrzak;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.arturpetrzak.view.ViewFactory;


/**
 * JavaFX App
 */
public class Launcher extends Application {

    @Override
    public void start(Stage stage) {

        ViewFactory viewFactory = new ViewFactory();
        viewFactory.showMainWindow();
    }

    public static void main(String[] args) {
        launch();
    }

}