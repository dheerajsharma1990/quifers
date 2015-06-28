package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class UserIdAttributeValidatorTest {

    private final UserIdAttributeValidator validator = new UserIdAttributeValidator();

    @Test
    public void shouldThrowExceptionForEmptyUserId() {
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("UserId is empty."));
        }

    }

    @Test
    public void shouldThrowExceptionForNullUserId() {
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("UserId is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForShortUserId() {
        try {
            validator.validate("small");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("[small] is too short.Minimum length is 8."));
        }
    }

    @Test
    public void shouldThrowExceptionForLongUserId() {
        try {
            validator.validate("mentionedUserIdIsVeryVeryVeryLong");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("[mentionedUserIdIsVeryVeryVeryLong] is too long.Maximum length is 30."));
        }
    }

    @Test
    public void shouldPassAllValidationAndReturnedTrimmedUserId() throws InvalidRequestException {
        //when
        String userId = validator.validate(" myUserId ");

        //then
        assertThat(userId, is("myUserId"));
    }

}
