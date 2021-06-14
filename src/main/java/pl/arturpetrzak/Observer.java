package pl.arturpetrzak;

import pl.arturpetrzak.controller.Location;

public interface Observer {
    void update(Location location, String country, String city, String weatherMessage);

    void displayMessage(String message);
}
