package com.quifers.servlet.admin.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.request.admin.AssignFieldExecutiveRequest;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.validations.OrderIdAttributeValidator;
import com.quifers.servlet.validations.UserIdAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AssignFieldExecutiveRequestValidatorTest {

    private final UserIdAttributeValidator userIdAttributeValidator = mock(UserIdAttributeValidator.class);
    private final OrderIdAttributeValidator orderIdAttributeValidator = mock(OrderIdAttributeValidator.class);

    private final AssignFieldExecutiveRequestValidator validator = new AssignFieldExecutiveRequestValidator(userIdAttributeValidator, orderIdAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId("fieldExecutiveId");
        OrderId orderId = new OrderId("orderId");
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn(fieldExecutiveId.getUserId());
        when(servletRequest.getParameter("order_id")).thenReturn(orderId.getOrderId());
        when(userIdAttributeValidator.validate(fieldExecutiveId.getUserId())).thenReturn(fieldExecutiveId.getUserId());
        when(orderIdAttributeValidator.validate(orderId.getOrderId())).thenReturn(orderId);

        //when
        AssignFieldExecutiveRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(fieldExecutiveId.getUserId());
        verify(orderIdAttributeValidator, times(1)).validate(orderId.getOrderId());
        assertThat(request.getFieldExecutiveId(), is(fieldExecutiveId));
        assertThat(request.getOrderId(), is(orderId));
    }

}