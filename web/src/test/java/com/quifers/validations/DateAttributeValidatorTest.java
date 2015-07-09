package com.quifers.validations;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class DateAttributeValidatorTest {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);

    private final DateAttributeValidator validator = new DateAttributeValidator(emptyStringAttributeValidator);

    @Test
    public void shouldThrowExceptionForInvalidDateFormat() {
        try {
            //given
            String invalidDate = "26-06-2015";
            when(emptyStringAttributeValidator.validate(invalidDate)).thenReturn(invalidDate);

            //when
            validator.validate(invalidDate);
            Assert.fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Date [26-06-2015] has invalid format.Correct format is [dd/MM/yyyy HH:mm]."));
        }
    }

    @Test
    public void shouldPassValidationForCorrectDateFormat() throws InvalidRequestException, ParseException {
        //given
        String validDate = "  25/07/2015 15:05";
        when(emptyStringAttributeValidator.validate(validDate)).thenReturn(validDate);

        //when
        Date date = validator.validate(validDate);

        //then
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 6, 25, 15, 5, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        assertThat(date, is(calendar.getTime()));
    }

}
