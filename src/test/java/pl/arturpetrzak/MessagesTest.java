package pl.arturpetrzak;

import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

class MessagesTest {

    @Test
    void shouldReturnPreparedTemperatureMessage() {

        //given
        String expectedResult = "Temperatures:\nMax: 20 °C\nMin: 10 °C";

        //when
        String result = Messages.getTemperatureRepresentation("10", "20", "C");

        //then
        assertThat(result, is(equalTo(expectedResult)));
    }
}