package pl.arturpetrzak.controller.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Optional;

public class PersistenceAccess {
    private String settingsLocation;
    private final ObjectMapper objectMapper;

    public PersistenceAccess(String path) {
        objectMapper = new ObjectMapper();
        settingsLocation = path;
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
}
