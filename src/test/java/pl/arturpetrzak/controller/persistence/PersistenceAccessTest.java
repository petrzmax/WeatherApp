package pl.arturpetrzak.controller.persistence;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import pl.arturpetrzak.Languages;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class PersistenceAccessTest {

    private PersistenceAccess persistenceAccess;

    @BeforeEach
    void setup() {
        persistenceAccess = new PersistenceAccess();
    }

    @Test
    void shouldReturnEmptyOptionalWhenSettingsFileDoesNotExist() {

        //given
        persistenceAccess.setSettingsLocation("path");

        //when
        //then
        assertThat(persistenceAccess.loadFromPersistence(), is(equalTo(Optional.empty())));
    }

    @Test
    void shouldReturnEmptyOptionalWhenSettingsFileCantBeRead() {

        //given
        String path = PersistenceAccessTest.class.getClassLoader().getResource("json/persistenceEmpty.json").getPath();
        persistenceAccess.setSettingsLocation(path);

        //when
        //then
        assertThat(persistenceAccess.loadFromPersistence(), is(equalTo(Optional.empty())));
    }

    @Test
    void shouldReturnLoadedSettingsObject() {

        //given
        String path = PersistenceAccessTest.class.getClassLoader().getResource("json/persistenceValid.json").getPath();
        persistenceAccess.setSettingsLocation(path);

        //when
        Optional<Settings> settings = persistenceAccess.loadFromPersistence();

        //then
        assertThat(settings.isPresent(), is(true));
        assertThat(settings.get().getIpstackApiKey(), is(equalTo("ipstackApiKey")));
        assertThat(settings.get().getAccuweatherApiKey(), is(equalTo("accuweatherApiKey")));
        assertThat(settings.get().isUsingMetricUnits(), is(true));
        assertThat(settings.get().getLanguage(), is(equalTo(Languages.POLISH)));
    }

    @Test
    void shouldSaveSettingsObjectToFile() throws IOException {

        //given
        String path = System.getProperty("user.home") + File.separator + "settingsTest.json";
        persistenceAccess.setSettingsLocation(path);
        ObjectMapper objectMapper = new ObjectMapper();

        Settings settings = new Settings();
        settings.setIpstackApiKey("setIpstackApiKey");
        settings.setAccuweatherApiKey("setAccuweatherApiKey");
        settings.setLanguage(Languages.GERMAN);
        settings.setUsingMetricUnits(false);

        //when
        persistenceAccess.saveToPersistence(settings);

        //then
        File settingsFile = new File(path);
        Settings savedSettings = objectMapper.readValue(settingsFile, Settings.class);

        assertThat(settingsFile.exists(), is(true));
        assertThat(savedSettings.getIpstackApiKey(), is(equalTo("setIpstackApiKey")));
        assertThat(savedSettings.getAccuweatherApiKey(), is(equalTo("setAccuweatherApiKey")));
        assertThat(savedSettings.getLanguage(), is(equalTo(Languages.GERMAN)));
        assertThat(savedSettings.isUsingMetricUnits(), is(false));

        settingsFile.delete();
    }




 }