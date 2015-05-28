package com.quifers.email.util;

import com.quifers.email.builders.EmailRequestBuilder;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmailRequestBuilderTest {

    private final EmailRequestBuilder builder = new EmailRequestBuilder();

    @Test
    public void shouldBuildEmailRequest() {
        //when
        String request = builder.buildEmailRequest("Yt6Rf");

        //then
        assertThat(request, is("{\"raw\":\"Yt6Rf\"}"));
    }
}
