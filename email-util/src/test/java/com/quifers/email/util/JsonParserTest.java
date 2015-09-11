package com.quifers.email.util;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class JsonParserTest {

    private final JsonParser parser = new JsonParser();

    @Test
    public void shouldParseRefreshTokenResponse() {
        //given
        String response = "{\n" +
                "  \"access_token\":\"1/fFBGRNJru1FQd44AzqT3Zg\",\n" +
                "  \"expires_in\":3920,\n" +
                "  \"token_type\":\"Bearer\",\n" +
                "}";

        //when
        Credentials credentials = parser.parseRefreshResponse("1234",response);

        //then
        assertThat(credentials.getAccessToken(), is("1/fFBGRNJru1FQd44AzqT3Zg"));
        assertThat(credentials.getRefreshToken(), is("1234"));
        assertThat(credentials.getTokenType(), is("Bearer"));
        assertThat(credentials.getExpiry(), is(3920));
    }
}
