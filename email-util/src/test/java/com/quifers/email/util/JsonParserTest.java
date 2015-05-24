package com.quifers.email.util;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public class JsonParserTest {

    private final JsonParser parser = new JsonParser();

    @Test
    public void shouldParseTokenResponse() {
        //given
        String response = "{\n" +
                "  \"access_token\" : \"ya29.fQHRsvnVkusn1Js0lt6t-KHNuq2uooz-UPRq6kLlprMv2MSV9-zFEWtwFrjDEV-jGFr_UL3qevbPtg\",\n" +
                "  \"token_type\" : \"Bearer\",\n" +
                "  \"expires_in\" : 3600,\n" +
                "  \"refresh_token\" : \"1/FULbjhL9F8U21IoB3IDU5SbMK-pt2BsMQe_FxN_cS7UMEudVrK5jSpoR30zcRFq6\"\n" +
                "}";

        //when
        Credentials credentials = parser.parse(response);

        //then
        assertThat(credentials.getAccessToken(), is("ya29.fQHRsvnVkusn1Js0lt6t-KHNuq2uooz-UPRq6kLlprMv2MSV9-zFEWtwFrjDEV-jGFr_UL3qevbPtg"));
        assertThat(credentials.getRefreshToken(), is("1/FULbjhL9F8U21IoB3IDU5SbMK-pt2BsMQe_FxN_cS7UMEudVrK5jSpoR30zcRFq6"));
        assertThat(credentials.getTokenType(), is("Bearer"));
        assertThat(credentials.getExpiry(), is(3600));
        assertThat(credentials.getGrantTime(), notNullValue());
    }
}
