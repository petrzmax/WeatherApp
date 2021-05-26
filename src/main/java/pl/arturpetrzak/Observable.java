package pl.arturpetrzak;

import pl.arturpetrzak.controller.Location;

public interface Observable {
    void addObserver (Observer observer);
    void removeObserver (Observer observer);
    void notifyObservers(Location location, String country, String city, String weatherMessage);
    void pushMessage(String message);
}
