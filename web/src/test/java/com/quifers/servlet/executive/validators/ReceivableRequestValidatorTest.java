package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.OrderId;
import com.quifers.request.executive.ReceivableRequest;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.OrderIdAttributeValidator;
import com.quifers.validations.PositiveIntegerAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class ReceivableRequestValidatorTest {

    private final OrderIdAttributeValidator orderIdAttributeValidator = mock(OrderIdAttributeValidator.class);
    private final PositiveIntegerAttributeValidator positiveIntegerAttributeValidator = mock(PositiveIntegerAttributeValidator.class);

    private final ReceivableRequestValidator validator = new ReceivableRequestValidator(orderIdAttributeValidator, positiveIntegerAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException, ParseException {
        //given
        OrderId orderId = new OrderId("QUIFID");
        int receivables = 500;
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("order_id")).thenReturn(orderId.getOrderId());
        when(servletRequest.getParameter("receivables")).thenReturn(String.valueOf(receivables));
        when(orderIdAttributeValidator.validate(orderId.getOrderId())).thenReturn(orderId);
        when(positiveIntegerAttributeValidator.validate(String.valueOf(receivables))).thenReturn(receivables);

        //when
        ReceivableRequest request = validator.validateRequest(servletRequest);

        //then
        verify(orderIdAttributeValidator, times(1)).validate(orderId.getOrderId());
        verify(positiveIntegerAttributeValidator, times(1)).validate(String.valueOf(receivables));
        assertThat(request.getOrderId(), is(orderId));
        assertThat(request.getReceivables(), is(receivables));
    }

}
