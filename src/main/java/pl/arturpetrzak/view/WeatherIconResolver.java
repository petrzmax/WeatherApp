package pl.arturpetrzak.view;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.arturpetrzak.Config;

import java.io.IOException;
import java.io.InputStream;

public class WeatherIconResolver {

    public ImageView getIconForWeather(int iconNumber) {
        String path = "icons/";
        try {
            switch (iconNumber) {
                case 1: // Sunny
                case 2: // Mostly Sunny
                case 3: // Partly Sunny
                    path += "day/sunny.png";
                    break;
                case 4: // Intermittent Clouds
                case 5: // Hazy Sunshine
                case 6: // Mostly Cloudy
                    path += "day/clouds.png";
                    break;
                case 7: // Cloudy
                case 8: // Dreary (Overcast)
                    path += "simple/clouds.png";
                    break;
                case 11: // Fog
                    path += "simple/fog.png";
                    break;
                case 12: // Showers
                    path += "simple/weak_rain.png";
                    break;
                case 13: // Mostly Cloudy w/ Showers
                case 14: // Partly Sunny w/ Showers
                    path += "day/rain.png";
                    break;
                case 15: // T-Storms
                    path += "simple/storm.png";
                    break;
                case 16: // Mostly Cloudy w/ T-Storms
                case 17: // Partly Sunny w/ T-Storms
                    path += "day/storm.png";
                    break;
                case 18: // Rain
                    path += "simple/strong_rain.png";
                    break;
                case 19: // Flurries
                case 22: // Snow
                case 25: // Sleet
                    path += "simple/snowing.png";
                    break;
                case 20: // Mostly Cloudy w/ Flurries
                case 21: // Partly Sunny w/ Flurries
                case 23: // Mostly Cloudy w/ Snow
                case 26: // Freezing Rain
                case 29: // Rain and Snow
                    path += "day/snowing.png";
                    break;
                case 24: // Ice
                    path += "simple/hail.png";
                    break;
                case 30: // Hot
                    path += "simple/hot.png";
                    break;
                case 31: // Cold
                    path += "simple/cold.png";
                case 32: // Windy
                    path += "simple/windy.png";
                    break;

                // Night
                case 33: // Clear
                case 34: // Mostly Clear
                case 35: // Partly Cloudy
                    path += "night/moon.png";
                    break;
                case 36: // Intermittent Clouds
                case 37: // Hazy Moonlight
                case 38: // Mostly Cloudy
                    path += "night/clouds.png";
                    break;
                case 39: // Partly Cloudy w/ Showers
                case 40: // Mostly Cloudy w/ Showers
                    path += "night/rain.png";
                    break;
                case 41: // Partly Cloudy w/ T-Storms
                case 42: // Mostly Cloudy w/ T-Storms
                    path += "night/storm.png";
                    break;
                case 43: // Mostly Cloudy w/ Flurries
                case 44: // Mostly Cloudy w/ Snow
                    path += "night/snowing.png";
                    break;
                default:
                    throw new IllegalArgumentException("Passed unsupported icon number: " + iconNumber);
            }

            InputStream inputStream = getClass().getResourceAsStream(path);
            if (inputStream == null) {
                throw new IOException("Path: " + path + " do not exist");
            }

            ImageView imageView = new ImageView(new Image(inputStream));
            imageView.setPreserveRatio(true);
            imageView.setFitHeight(Config.getIconSize());
            imageView.setSmooth(true);
            imageView.setCache(true);

            return imageView;

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }
}
