package com.quifers.email.util;

import com.quifers.email.builders.AccessTokenRefreshRequestBuilder;
import com.quifers.email.properties.EmailUtilProperties;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccessTokenRefreshRequestBuilderTest {

    private EmailUtilProperties emailUtilProperties = mock(EmailUtilProperties.class);
    private final AccessTokenRefreshRequestBuilder builder = new AccessTokenRefreshRequestBuilder(emailUtilProperties);

    @Test
    public void shouldBuildAccessTokenRefreshRequest() throws Exception {
        //given
        when(emailUtilProperties.getClientId()).thenReturn("11.22");
        when(emailUtilProperties.getClientSecretKey()).thenReturn("44.55");

        //when
        String request = builder.buildAccessTokenRefreshRequest("55.66");

        //then
        assertThat(request, is("refresh_token=55.66&" +
                "client_id=11.22&" +
                "client_secret=44.55&" +
                "grant_type=refresh_token"));
    }
}
