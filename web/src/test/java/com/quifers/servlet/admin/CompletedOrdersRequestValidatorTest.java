package com.quifers.servlet.admin;

import com.quifers.request.validators.InvalidRequestException;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class CompletedOrdersRequestValidatorTest {

    private final CompletedOrdersRequestValidator validator = new CompletedOrdersRequestValidator();

    @Test
    public void shouldPassAllValidation() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("begin_booking_day")).thenReturn("28/06/2015");
        when(servletRequest.getParameter("end_booking_day")).thenReturn("30/06/2015");

        //when
        CompletedOrdersRequest request = validator.validateRequest(servletRequest);
        SimpleDateFormat dateFormat = new SimpleDateFormat(CompletedOrdersRequestValidator.FORMAT);

        //then
        assertThat(request.getBeginBookingDate(), is(dateFormat.parse("28/06/2015")));
        assertThat(request.getEndBookingDate(), is(dateFormat.parse("01/07/2015")));
    }

    @Test
    public void shouldThrowExceptionOnNullDate() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("begin_booking_day")).thenReturn(null);
        when(servletRequest.getParameter("end_booking_day")).thenReturn("31/06/2015");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }

    }

    @Test
    public void shouldThrowExceptionOnInvalidDateFormatDate() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("begin_booking_day")).thenReturn("28/06/2015");
        when(servletRequest.getParameter("end_booking_day")).thenReturn("30-06-2015");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }

    }

}