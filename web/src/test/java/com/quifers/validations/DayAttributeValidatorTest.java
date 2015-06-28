package com.quifers.validations;

import com.quifers.domain.Day;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class DayAttributeValidatorTest {

    private final DayAttributeValidator validator = new DayAttributeValidator();

    @Test
    public void shouldThrowExceptionForEmptyDay() {
        try {
            validator.validate("");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Day is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForNullDay() {
        try {
            validator.validate(null);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Day is empty."));
        }
    }

    @Test
    public void shouldThrowExceptionForInvalidDayFormat() {
        try {
            validator.validate("26-06-2015");
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Invalid day [26-06-2015].Correct format is [dd/MM/yyyy]."));
        }
    }

    @Test
    public void shouldPassValidationForCorrectDayFormat() throws InvalidRequestException, ParseException {
        //when
        Day day = validator.validate("  25/07/2015 ");

        //then
        assertThat(day, is(new Day("25/07/2015")));
    }

}
