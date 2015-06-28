package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class AccessTokenAttributeValidatorTest {

    private final AccessTokenAttributeValidator validator = new AccessTokenAttributeValidator();

    @Test
    public void shouldThrowExceptionOnEmptyAccessToken() {
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Access Token is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionOnNullAccessToken() {
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Access Token is empty."));
        }
    }

    @Test
    public void shouldPassAllValidationAndReturnExactAccessToken() throws InvalidRequestException {
        //when
        String accessToken = validator.validate(" acc-ss Toke n ");

        //then
        assertThat(accessToken, is(" acc-ss Toke n "));
    }
}
