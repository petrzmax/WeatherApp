package pl.arturpetrzak.model;

import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class LocationForecastTest {

    @GivenTextResource("json/accuWeatherForecastResponse.json")
    protected String serverResponse;

    @Test
    void shouldLoadWeatherForecastResponse() {
        //given
        JSONObject jsonObject = new JSONObject(serverResponse);

        //when
        LocationForecast locationForecast = new LocationForecast(jsonObject);

        //then
        assertThat(locationForecast.getWeatherMessage(), is(equalTo(
                "Thunderstorms in the area late Thursday night through Friday afternoon"
        )));
        assertThat(locationForecast.getDailyForecasts().size(), is(equalTo(5)));
    }
}