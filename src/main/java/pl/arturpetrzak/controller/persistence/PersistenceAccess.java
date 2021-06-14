package pl.arturpetrzak.controller.persistence;

import pl.arturpetrzak.Config;

import java.io.*;
import java.util.Optional;

public class PersistenceAccess {
    private final String SETTINGS_LOCATION = System.getProperty("user.home") + File.separator + Config.getAppName() + "Settings.ser";

    public Optional<Settings> loadFromPersistence() {
        try {
            Settings settings;
            FileInputStream fileInputStream = new FileInputStream(SETTINGS_LOCATION);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            settings = (Settings) objectInputStream.readObject();
            return Optional.of(settings);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void saveToPersistence(Settings settings) {
        try {
            File file = new File(SETTINGS_LOCATION);
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(settings);
            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
