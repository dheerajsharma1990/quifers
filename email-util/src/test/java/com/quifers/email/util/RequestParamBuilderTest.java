package com.quifers.email.util;

import org.hamcrest.MatcherAssert;
import org.testng.annotations.Test;

import static org.hamcrest.core.Is.is;

public class RequestParamBuilderTest {
    @Test
    public void shouldBuildParamsCorrectlyForMultipleParameters() throws Exception {
        //given
        RequestParamBuilder builder = new RequestParamBuilder();
        builder.addParam("name", "value");
        builder.addParam("otherName", "other@value");

        //when
        String request = builder.build();

        //then
        MatcherAssert.assertThat(request, is("name=value&otherName=other%40value"));
    }

    @Test
    public void shouldParamsCorrectlyForSingleParameter() throws Exception {
        //given
        RequestParamBuilder builder = new RequestParamBuilder();
        builder.addParam("name", "value");

        //when
        String request = builder.build();

        //then
        MatcherAssert.assertThat(request, is("name=value"));
    }
}
