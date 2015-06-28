package com.quifers.servlet.executive.validators;

import com.quifers.domain.Day;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.executive.GetOrdersRequest;
import com.quifers.validations.DayAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.UserIdAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class GetOrdersRequestValidatorTest {

    private final UserIdAttributeValidator userIdAttributeValidator = mock(UserIdAttributeValidator.class);
    private final DayAttributeValidator dayAttributeValidator = mock(DayAttributeValidator.class);
    private final GetOrdersRequestValidator validator = new GetOrdersRequestValidator(userIdAttributeValidator, dayAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException, ParseException {
        //given
        String userId = "userId";
        String bookingDay = "25/09/2015";
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn(userId);
        when(servletRequest.getParameter("booking_date")).thenReturn(bookingDay);
        when(userIdAttributeValidator.validate(userId)).thenReturn(userId);
        when(dayAttributeValidator.validate(bookingDay)).thenReturn(new Day(bookingDay));

        //when
        GetOrdersRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(userId);
        verify(dayAttributeValidator, times(1)).validate(bookingDay);
        assertThat(request.getFieldExecutiveId(), is(new FieldExecutiveId(userId)));
        assertThat(request.getBookingDate(), is(new Day(bookingDay)));
    }

}
