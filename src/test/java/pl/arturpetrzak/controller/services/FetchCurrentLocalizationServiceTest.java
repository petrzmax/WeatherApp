package pl.arturpetrzak.controller.services;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import okhttp3.*;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class FetchCurrentLocalizationServiceTest {

    @Mock
    private OkHttpClient okHttpClient;

    @Mock
    private Response response;

    @Mock
    private ResponseBody responseBody;

    @Mock
    private Call call;

    @InjectMocks
    private FetchCurrentLocalizationService fetchCurrentLocalizationService = new FetchCurrentLocalizationService(okHttpClient);

    @GivenTextResource("json/ipStackSuccessResponse.json")
    protected String serverSuccessResponse;

    @GivenTextResource("json/ipStackSuccessFalseResponse.json")
    protected String serverSuccessFalseResponse;

    @BeforeAll
    static void setup() {
        MockedStatic<Config> config = Mockito.mockStatic(Config.class);
        config.when(Config::getIpstackApiKey).thenReturn("TestApiKey");
        config.when(Config::getCurrentCityByIpApiUrl).thenReturn("https://TestApiUrl");
    }

    @Test
    void shouldReturnCurrentLocalization() throws Exception {

        //given
        given(okHttpClient.newCall(any(Request.class))).willReturn(call);
        given(call.execute()).willReturn(response);
        given(response.code()).willReturn(200);
        given(response.body()).willReturn(responseBody);
        given(responseBody.string()).willReturn(serverSuccessResponse);

        //when
        FetchDataResult fetchDataResult = fetchCurrentLocalizationService.fetchData();

        assertThat(fetchDataResult, is(equalTo(FetchDataResult.SUCCESS)));
        assertThat(fetchCurrentLocalizationService.getCity(), is(equalTo("\u015ar\u00f3dmie\u015bcie")));
        assertThat(fetchCurrentLocalizationService.getCountry(), is(equalTo("Poland")));
    }

    @Test
    void shouldReturnParsedJsonAsJsonObject() throws Exception {

        //when
        JSONObject jsonObject = fetchCurrentLocalizationService.parseResponse(serverSuccessResponse);

        //then
        assertThat(jsonObject.isEmpty(), is(equalTo(false)));
        assertThat(jsonObject.getString("continent_name"), is(equalTo("Europe")));
        assertThat(jsonObject.getJSONObject("location").getString("capital"), is(equalTo("Warsaw")));
    }

    @Test
    void shouldThrowExceptionWithProperMessageWhenResponseHasFalseSuccessFlag() {

        //when
        //then
        Exception exception = assertThrows(Exception.class, () -> {
            fetchCurrentLocalizationService.parseResponse(serverSuccessFalseResponse);
        });

        assertThat(exception.getMessage(), is(equalTo(
                "HttpRequestCode: 101 - You have not supplied a valid API Access Key. [Technical Support: support@apilayer.com]"
        )));
    }

    @Test
    void shouldThrowExceptionWithProperMessageWhenResponseCodeIsDifferentThan200() {
        //given
        given(okHttpClient.newCall(any(Request.class))).willReturn(call);
        given(response.code()).willReturn(199);

        try {
            given(call.execute()).willReturn(response);

            //when
            fetchCurrentLocalizationService.fetchData();

        } catch (IOException exception) {

            //then
            assertThat(exception, is(sameInstance(IOException.class)));
            assertThat(exception.getMessage(), is(equalTo("HttpRequestCode: 199")));

        }
    }

    @Test
    void shouldReturnSuccessFetchDataResult() throws Exception {
        //given
        given(okHttpClient.newCall(any(Request.class))).willReturn(call);
        given(response.code()).willReturn(200);
        given(call.execute()).willReturn(response);
        given(response.body()).willReturn(responseBody);
        given(responseBody.string()).willReturn(serverSuccessResponse);

        assertThat(fetchCurrentLocalizationService.fetchData(), is(equalTo(FetchDataResult.SUCCESS)));
    }


    @Test
    void shouldReturnFailedByRequestSyntaxForCode400() {

        //when
        FetchDataResult fetchDataResult = fetchCurrentLocalizationService.getFetchDataResultByResponseCode(400);

        //then
        assertThat(fetchDataResult, is(equalTo(FetchDataResult.FAILED_BY_REQUEST_SYNTAX)));
    }

    @ParameterizedTest
    @ValueSource(ints = {401, 403, 503})
    void shouldReturnFailedByApi(int responseCode) {

        //when
        FetchDataResult fetchDataResult = fetchCurrentLocalizationService.getFetchDataResultByResponseCode(responseCode);

        //then
        assertThat(fetchDataResult, is(equalTo(FetchDataResult.FAILED_BY_API_AUTHORIZATION)));
    }

    @ParameterizedTest
    @ValueSource(ints = {404, 500})
    void shouldReturnFailedByServers(int responseCode) {

        //when
        FetchDataResult fetchDataResult = fetchCurrentLocalizationService.getFetchDataResultByResponseCode(responseCode);

        //then
        assertThat(fetchDataResult, is(equalTo(FetchDataResult.FAILED_BY_SERVER)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 222, 333, 444, 555})
    void shouldReturnFailedByUnexpectedError(int responseCode) {

        //when
        FetchDataResult fetchDataResult = fetchCurrentLocalizationService.getFetchDataResultByResponseCode(responseCode);

        //then
        assertThat(fetchDataResult, is(equalTo(FetchDataResult.FAILED_BY_UNEXPECTED_ERROR)));
    }
}