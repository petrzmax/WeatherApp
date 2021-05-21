package pl.arturpetrzak.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import pl.arturpetrzak.model.DailyForecast;

public class DailyForecastRepresentation {
    public VBox getDailyForecastRepresentation(DailyForecast dailyForecast, ImageView dayImageView, ImageView nightImageView) {

        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMinWidth(130);
        vBox.setMaxHeight(200);

        vBox.getChildren().add(dayImageView);

        Label label = new Label(dailyForecast.getDayWeatherDescription());
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMinHeight(40);

        vBox.getChildren().add(label);
        vBox.getChildren().add(new Label(dailyForecast.getDate()));
        vBox.getChildren().add(new Label("Temperatures:"));
        vBox.getChildren().add(new Label("Max: " + dailyForecast.getMaximumTemperature() + dailyForecast.getUnit()));
        vBox.getChildren().add(new Label("Min: " + dailyForecast.getMinimumTemperature() + dailyForecast.getUnit()));

        vBox.getChildren().add(nightImageView);
        label = new Label(dailyForecast.getNightWeatherDescription());
        label.setWrapText(true);
        label.setMinHeight(40);
        vBox.getChildren().add(label);

        return vBox;
    }
}
