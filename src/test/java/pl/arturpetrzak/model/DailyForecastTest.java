package pl.arturpetrzak.model;

import com.adelean.inject.resources.junit.jupiter.GivenJsonResource;
import com.adelean.inject.resources.junit.jupiter.GivenTextResource;
import com.adelean.inject.resources.junit.jupiter.TestWithResources;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;

@TestWithResources
@ExtendWith(MockitoExtension.class)
class DailyForecastTest {

    @GivenTextResource("json/accuWeatherOneDayForecast.json")
    String oneDayForecast;

    @Test
    void createdDailyForecastObjectShouldHaveJsonDataLoadedToVariables() {

        //given
        DailyForecast dailyForecast = new DailyForecast(new JSONObject(oneDayForecast));

        //when
        //then
        assertThat(dailyForecast.getDate(), is(equalTo("21-06")));
        assertThat(dailyForecast.getUnit(), is(equalTo("C")));
        assertThat(dailyForecast.getMinimumTemperature(), is(equalTo("19.8")));
        assertThat(dailyForecast.getMaximumTemperature(), is(equalTo("31.9")));
        assertThat(dailyForecast.getDayWeatherDescription(), is(equalTo("Partly sunny w/ t-storms")));
        assertThat(dailyForecast.getNightWeatherDescription(), is(equalTo("Partly cloudy")));
        assertThat(dailyForecast.getDayIconNumber(), is(equalTo(17)));
        assertThat(dailyForecast.getNightIconNumber(), is(equalTo(35)));
    }

}