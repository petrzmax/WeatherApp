package pl.arturpetrzak.controller.services;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import okhttp3.*;
import org.json.JSONObject;
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
import pl.arturpetrzak.controller.FetchDataResult;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class FetchWeatherServiceTest {
    @Mock
    private OkHttpClient okHttpClient;

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    @Mock
    private Call call;

    @InjectMocks
    private FetchWeatherService fetchWeatherService = new FetchWeatherService(okHttpClient);

    @GivenTextResource("json/accuWeatherCityDataResponse.json")
    protected String serverResponse;

    @GivenTextResource("json/accuWeatherCityDataParsedResponse.json")
    protected String parsedServerResponse;

    @BeforeAll
    static void setup() {
        MockedStatic<Config> config = Mockito.mockStatic(Config.class);
        config.when(Config::getAccuWeatherApiKey).thenReturn("TestApiKey");
        config.when(Config::getDailyWeatherForecastApiUrl).thenReturn("https://TestApiUrl");
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