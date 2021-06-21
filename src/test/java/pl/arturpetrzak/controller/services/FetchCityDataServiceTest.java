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
import pl.arturpetrzak.controller.FetchDataResult;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class FetchCityDataServiceTest {

    @Mock
    private OkHttpClient okHttpClient;

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    @Mock
    private Call call;

    @InjectMocks
    private FetchCityDataService fetchCityDataService = new FetchCityDataService(okHttpClient);

    @GivenTextResource("json/accuWeatherCityDataResponse.json")
    protected String serverResponse;

    @GivenTextResource("json/accuWeatherCityDataParsedResponse.json")
    protected String parsedServerResponse;

    @BeforeAll
    static void setup() {
        MockedStatic<Config> config = Mockito.mockStatic(Config.class);
        config.when(Config::getAccuWeatherApiKey).thenReturn("TestApiKey");
        config.when(Config::getCitySearchApiUrl).thenReturn("https://TestApiUrl");
    }

    @Test
    void shouldBuildProperUrl() {

        //given
        fetchCityDataService.setCity("TestCity");
        fetchCityDataService.setCountry("TestCountry");

        //when
        fetchCityDataService.buildUrl();

        //then
        assertThat(fetchCityDataService.url, is(equalTo("https://TestApiUrl?q=TestCountry,TestCity&apikey=TestApiKey")));
    }

    @Test
    void shouldReturnFirstObjectFromJsonArrayAsJsonObject() {

        //given
        JSONObject parsedJsonObject = new JSONObject(parsedServerResponse);

        //when
        JSONObject jsonObject = fetchCityDataService.parseResponse(serverResponse);

        //then
        assertThat(jsonObject.isEmpty(), is(false));
        assertThat(jsonObject.toString(), equalTo(parsedJsonObject.toString()));

    }

    @Test
    void shouldReturnProperCityId() throws IOException {

        //given
        given(okHttpClient.newCall(any(Request.class))).willReturn(call);
        given(call.execute()).willReturn(response);
        given(response.code()).willReturn(200);
        given(response.body()).willReturn(responseBody);
        given(responseBody.string()).willReturn(serverResponse);

        //when
        FetchDataResult fetchDataResult = fetchCityDataService.fetchData();

        assertThat(fetchDataResult, is(equalTo(FetchDataResult.SUCCESS)));
        assertThat(fetchCityDataService.getCityId(), is(equalTo("264658")));
    }
}