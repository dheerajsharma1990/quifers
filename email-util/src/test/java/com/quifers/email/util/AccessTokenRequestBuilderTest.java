package com.quifers.email.util;

import com.quifers.email.builders.AccessTokenRequestBuilder;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AccessTokenRequestBuilderTest {

    private final AccessTokenRequestBuilder builder = new AccessTokenRequestBuilder();

    @Test
    public void shouldBuildAccessTokenRequest() throws Exception {
        //when
        String request = builder.buildAccessTokenRequest("11-22", "http://localhost:8008/callback", "22-33", "44-55");

        //then
        assertThat(request, is("code=11-22&" +
                "client_id=22-33&" +
                "client_secret=44-55&" +
                "redirect_uri=http%3A%2F%2Flocalhost%3A8008%2Fcallback&" +
                "grant_type=authorization_code"));
    }
}
