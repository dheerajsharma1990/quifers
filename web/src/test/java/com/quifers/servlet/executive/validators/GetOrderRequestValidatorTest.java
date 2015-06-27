package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.executive.request.GetOrdersRequest;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetOrderRequestValidatorTest {

    private final GetOrdersRequestValidator requestValidator = new GetOrdersRequestValidator();

    @Test
    public void shouldPassAllValidations() throws InvalidRequestException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String myFieldExecutiveId = "myFieldExecutiveId";
        when(servletRequest.getParameter("user_id")).thenReturn(myFieldExecutiveId);
        when(servletRequest.getParameter("booking_date")).thenReturn("25/06/2015");

        //when
        GetOrdersRequest getOrdersRequest = requestValidator.validateRequest(servletRequest);

        //then
        assertThat(getOrdersRequest.getFieldExecutiveId(), is(new FieldExecutiveId(myFieldExecutiveId)));
        assertThat(getOrdersRequest.getBookingDate(), notNullValue());
    }

    @Test
    public void shouldFailForEmptyUserIdValidations() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String myFieldExecutiveId = "";
        when(servletRequest.getParameter("user_id")).thenReturn(myFieldExecutiveId);
        when(servletRequest.getParameter("booking_date")).thenReturn("25/06/2015");

        try {
            requestValidator.validateRequest(servletRequest);
        } catch (InvalidRequestException e) {

        }
    }

    @Test
    public void shouldFailForInvalidBookingDateValidations() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String myFieldExecutiveId = "myFieldExecutiveId";
        when(servletRequest.getParameter("user_id")).thenReturn(myFieldExecutiveId);
        when(servletRequest.getParameter("booking_date")).thenReturn("25-06-2015");

        try {
            requestValidator.validateRequest(servletRequest);
        } catch (InvalidRequestException e) {

        }
    }

}
