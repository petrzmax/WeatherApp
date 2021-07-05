package pl.arturpetrzak.controller.services;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.arturpetrzak.Config;
import pl.arturpetrzak.controller.FetchDataResult;

import java.io.IOException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class FetchCityDataServiceTest {

    @Mock
    private OkHttpClient okHttpClient;

    private MockedStatic<Config> config;

    @InjectMocks
    private FetchCityDataService fetchCityDataService = new FetchCityDataService(okHttpClient);

    @GivenTextResource("json/accuWeatherCityDataResponse.json")
    protected String serverResponse;

    @GivenTextResource("json/accuWeatherCityDataParsedResponse.json")
    protected String parsedServerResponse;

    @GivenTextResource("json/accuWeatherCityDataUnParsableResponse.json")
    protected String unParsableServerResponse;

    @BeforeEach
    void setup() {
        config = Mockito.mockStatic(Config.class);
        config.when(Config::getAccuWeatherApiKey).thenReturn("TestApiKey");
        config.when(Config::getCitySearchApiUrl).thenReturn("https://TestApiUrl");
    }

    @AfterEach
    void cleanUp() {
        config.close();
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
        assertThat(jsonObject.toString(), equalTo(parsedJsonObject.toString()));

    }

    @Test
    void shouldReturnProperCityId() throws IOException {

        //given
        mockHttpCall(200, this.serverResponse);

        //when
        FetchDataResult fetchDataResult = fetchCityDataService.fetchData();

        //then
        assertThat(fetchDataResult, is(equalTo(FetchDataResult.SUCCESS)));
        assertThat(fetchCityDataService.getCityId(), is(equalTo("264658")));
    }

    @Test
    void shouldReturnNullWhenResponseIsEmpty() {

        //given
        String emptyResponse = "[]";

        //when
        JSONObject jsonObject = fetchCityDataService.parseResponse(emptyResponse);

        //then
        assertThat(jsonObject, is(equalTo(null)));
    }

    @Test
    void shouldReturnEmptyResponseResultWhenResponseIsEmpty() throws IOException {

        //given
        mockHttpCall(200, "[]");

        //when
        FetchDataResult fetchDataResult = fetchCityDataService.fetchData();

        //then
        assertThat(fetchDataResult, is(equalTo(FetchDataResult.RESPONSE_EMPTY)));
    }


    @Test
    void shouldThrowExceptionWhenResponseIsUnParsable() {

        //given
        //when
        Executable executable = () -> fetchCityDataService.parseResponse(unParsableServerResponse);

        //then
        assertThrows(JSONException.class, executable);
    }

    private void mockHttpCall(int status, String serverResponse) throws IOException {

        Call call = mock(Call.class);
        Response response = mock(Response.class);
        ResponseBody responseBody = mock(ResponseBody.class);

        given(okHttpClient.newCall(any())).willReturn(call);
        given(call.execute()).willReturn(response);
        given(response.code()).willReturn(status);
        given(response.body()).willReturn(responseBody);
        given(responseBody.string()).willReturn(serverResponse);
    }
}