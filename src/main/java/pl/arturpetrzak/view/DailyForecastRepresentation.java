package pl.arturpetrzak.view;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import pl.arturpetrzak.model.DailyForecast;

public class DailyForecastRepresentation {
    public VBox getDailyForecastRepresentation(DailyForecast dailyForecast, ImageView dayImageView, ImageView nightImageView) {

        Label label;
        VBox vBox = new VBox();
        vBox.setAlignment(Pos.CENTER);
        vBox.setMaxWidth(100);

        // Date
        label = new Label(dailyForecast.getDate());
        label.setTextAlignment(TextAlignment.CENTER);
        label.setFont(Font.font(20));
        vBox.getChildren().add(label);

        // Day
        vBox.getChildren().add(dayImageView);
        label = new Label(dailyForecast.getDayWeatherDescription().replace("w/", "with"));


        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMaxHeight(-1);
        vBox.getChildren().add(label);

        // Night
        vBox.getChildren().add(nightImageView);
        label = new Label(dailyForecast.getNightWeatherDescription().replace("w/", "with"));
        label.setWrapText(true);
        label.setTextAlignment(TextAlignment.CENTER);
        label.setMinHeight(40);
        vBox.getChildren().add(label);

        // Weather description

        label = new Label("Temperatures:" +
                "\nMax: " + dailyForecast.getMaximumTemperature() + dailyForecast.getUnit() +
                "\nMin: " + dailyForecast.getMinimumTemperature() + dailyForecast.getUnit()
        );

        label.setTextAlignment(TextAlignment.CENTER);

        vBox.getChildren().add(label);

        return vBox;
    }
}
