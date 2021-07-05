package pl.arturpetrzak.controller.services;

import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.Languages;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class FetchWeatherServiceTest {

    @Mock
    private OkHttpClient okHttpClient;

    private static MockedStatic<Config> config;

    @InjectMocks
    private FetchWeatherService fetchWeatherService = new FetchWeatherService(okHttpClient);

    @BeforeAll
    static void setup() {
        config = Mockito.mockStatic(Config.class);
        config.when(Config::getAccuWeatherApiKey).thenReturn("TestApiKey");
        config.when(Config::getDailyWeatherForecastApiUrl).thenReturn("https://TestApiUrl");
    }

    @AfterEach
    void cleanUp() {
        config.close();
    }

    @Test
    void shouldBuildProperUrl() {

        //given
        fetchWeatherService.setCityId("264658");
        fetchWeatherService.setLanguage(Languages.ENGLISH);

        //when
        fetchWeatherService.buildUrl();

        //then
        assertThat(fetchWeatherService.url, is(equalTo("https://TestApiUrl264658?metric=false&language=en&apikey=TestApiKey")));
    }
}