package com.quifers.validations;

import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.validations.PasswordAttributeValidator;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class PasswordAttributeValidatorTest {

    private final PasswordAttributeValidator validator = new PasswordAttributeValidator();

    @Test
    public void shouldThrowExceptionForEmptyPassword() {
        //when
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Password is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForNullPassword() {
        //when
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Password is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForSpacesInPasswordPassword() {
        //when
        try {
            validator.validate("space password");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Password contains spaces."));
        }
    }

    @Test
    public void shouldThrowExceptionForShortPassword() {
        //when
        try {
            validator.validate("short");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Password is too short.Minimum length is [8]."));
        }
    }

    @Test
    public void shouldThrowExceptionForLongPassword() {
        //when
        try {
            validator.validate("mentionedPasswordIsExtremelyLong");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Password is too long.Maximum length is [20]."));
        }
    }

    @Test
    public void shouldPassValidationAndReturnPassword() throws InvalidRequestException {
        //given
        String givenPassword = "some@123password";

        //when
        String password = validator.validate(givenPassword);

        //then
        assertThat(password, is(givenPassword));

    }
}
