package com.quifers.email.util;

import com.quifers.email.builders.AccessCodeRequestBuilder;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AccessCodeRequestBuilderTest {

    private final AccessCodeRequestBuilder builder = new AccessCodeRequestBuilder();

    @Test
    public void shouldBuildAccessCodeRequest() throws Exception {
        //when
        String request = builder.buildAccessCodeRequest("12-23", "http://localhost:8008/callback", "some@email.com");

        //then
        assertThat(request, is("response_type=code&" +
                "client_id=12-23&" +
                "redirect_uri=http%3A%2F%2Flocalhost%3A8008%2Fcallback&" +
                "scope=https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fgmail.compose&" +
                "state=FirstAccessCode&" +
                "access_type=offline&" +
                "approval_prompt=force&" +
                "login_hint=some%40email.com"));
    }

}
