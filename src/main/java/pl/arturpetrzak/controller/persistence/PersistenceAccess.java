package pl.arturpetrzak.controller.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.arturpetrzak.Config;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

public class PersistenceAccess {
    private String settingsLocation;
    private final ObjectMapper objectMapper;

    public PersistenceAccess() {
        objectMapper = new ObjectMapper();
        setSettingsLocation(System.getProperty("user.home") + File.separator + Config.getAppName() + "Settings.ser");
    }

    public Optional<Settings> loadFromPersistence() {
        try {
            File file = new File(settingsLocation);

            Settings settings = objectMapper.readValue(file, Settings.class);

            return Optional.of(settings);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void saveToPersistence(Settings settings) {
        try {
            File file = new File(settingsLocation);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, settings);

            fileOutputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setSettingsLocation(String settingsLocation) {
        this.settingsLocation = settingsLocation;
    }
}
