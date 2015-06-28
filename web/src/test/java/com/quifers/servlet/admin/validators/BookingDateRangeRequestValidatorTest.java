package com.quifers.servlet.admin.validators;

import com.quifers.domain.Day;
import com.quifers.servlet.admin.request.BookingDateRangeRequest;
import com.quifers.servlet.validations.DayAttributeValidator;
import com.quifers.servlet.validations.InvalidRequestException;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class BookingDateRangeRequestValidatorTest {

    private DayAttributeValidator dayAttributeValidator = mock(DayAttributeValidator.class);
    private final BookingDateRangeRequestValidator validator = new BookingDateRangeRequestValidator(dayAttributeValidator);
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException, ParseException {
        //given
        String beginBookingDay = "25/06/2015";
        String endBookingDay = "28/06/2015";
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("begin_booking_day")).thenReturn(beginBookingDay);
        when(servletRequest.getParameter("end_booking_day")).thenReturn(endBookingDay);
        when(dayAttributeValidator.validate(beginBookingDay)).thenReturn(new Day(beginBookingDay));
        when(dayAttributeValidator.validate(endBookingDay)).thenReturn(new Day(endBookingDay));

        //when
        BookingDateRangeRequest request = validator.validateRequest(servletRequest);

        //then
        verify(dayAttributeValidator, times(1)).validate(beginBookingDay);
        verify(dayAttributeValidator, times(1)).validate(endBookingDay);
        assertThat(request.getBeginBookingDate(), is(dateFormat.parse(beginBookingDay)));
        assertThat(request.getEndBookingDate(), is(dateFormat.parse("29/06/2015")));
    }

}