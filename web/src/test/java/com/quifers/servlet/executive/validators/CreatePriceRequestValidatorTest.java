package com.quifers.servlet.executive.validators;

import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import com.quifers.domain.id.OrderWorkflowId;
import com.quifers.request.executive.CreatePriceRequest;
import com.quifers.validations.BooleanAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.OrderIdAttributeValidator;
import com.quifers.validations.PositiveIntegerAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;
import static org.mockito.Mockito.*;

public class CreatePriceRequestValidatorTest {

    private final OrderIdAttributeValidator orderIdAttributeValidator = mock(OrderIdAttributeValidator.class);
    private final PositiveIntegerAttributeValidator positiveIntegerAttributeValidator = mock(PositiveIntegerAttributeValidator.class);
    private final BooleanAttributeValidator booleanAttributeValidator = mock(BooleanAttributeValidator.class);

    private final CreatePriceRequestValidator validator = new CreatePriceRequestValidator(orderIdAttributeValidator, positiveIntegerAttributeValidator, booleanAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        OrderId orderId = new OrderId("QUIFID");
        int distance = 20;
        int waitingMinutes = 45;
        int pickUpFloors = 2;
        boolean pickUpLiftWorking = true;
        int dropOffFloors = 4;
        boolean dropOffLiftWorking = false;

        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("order_id")).thenReturn(orderId.getOrderId());
        when(servletRequest.getParameter("distance")).thenReturn(String.valueOf(distance));
        when(servletRequest.getParameter("waiting_minutes")).thenReturn(String.valueOf(waitingMinutes));
        when(servletRequest.getParameter("pick_up_floors")).thenReturn(String.valueOf(pickUpFloors));
        when(servletRequest.getParameter("pick_up_lift_working")).thenReturn(String.valueOf(pickUpLiftWorking));
        when(servletRequest.getParameter("drop_off_floors")).thenReturn(String.valueOf(dropOffFloors));
        when(servletRequest.getParameter("drop_off_lift_working")).thenReturn(String.valueOf(dropOffLiftWorking));
        when(orderIdAttributeValidator.validate(orderId.getOrderId())).thenReturn(orderId);
        when(positiveIntegerAttributeValidator.validate(String.valueOf(distance))).thenReturn(distance);
        when(positiveIntegerAttributeValidator.validate(String.valueOf(waitingMinutes))).thenReturn(waitingMinutes);
        when(positiveIntegerAttributeValidator.validate(String.valueOf(pickUpFloors))).thenReturn(pickUpFloors);
        when(booleanAttributeValidator.validate(String.valueOf(pickUpLiftWorking))).thenReturn(pickUpLiftWorking);
        when(positiveIntegerAttributeValidator.validate(String.valueOf(dropOffFloors))).thenReturn(dropOffFloors);
        when(booleanAttributeValidator.validate(String.valueOf(dropOffLiftWorking))).thenReturn(dropOffLiftWorking);

        //when
        CreatePriceRequest request = validator.validateRequest(servletRequest);

        //then
        verify(orderIdAttributeValidator, times(1)).validate(orderId.getOrderId());
        verify(positiveIntegerAttributeValidator, times(1)).validate(String.valueOf(distance));
        verify(positiveIntegerAttributeValidator, times(1)).validate(String.valueOf(waitingMinutes));
        verify(positiveIntegerAttributeValidator, times(1)).validate(String.valueOf(pickUpFloors));
        verify(booleanAttributeValidator, times(1)).validate(String.valueOf(pickUpLiftWorking));
        verify(positiveIntegerAttributeValidator, times(1)).validate(String.valueOf(dropOffFloors));
        verify(booleanAttributeValidator, times(1)).validate(String.valueOf(dropOffLiftWorking));
        assertThat(request.getOrderId(), is(orderId));
        assertThat(request.getDistance(), is(distance));
        assertThat(request.getWaitingMinutes(), is(waitingMinutes));
        assertThat(request.getPickupFloors(), is(pickUpFloors));
        assertThat(request.isPickupLiftWorking(), is(pickUpLiftWorking));
        assertThat(request.getDropOffFloors(), is(dropOffFloors));
        assertThat(request.isDropOffLiftWorking(), is(dropOffLiftWorking));
        OrderWorkflow orderWorkflow = request.getOrderWorkflow();
        assertThat(orderWorkflow, notNullValue());
        assertThat(orderWorkflow.getOrderWorkflowId(), is(new OrderWorkflowId(orderId.getOrderId(), OrderState.COMPLETED)));
        assertThat(orderWorkflow.isCurrentState(), is(true));
        assertThat(orderWorkflow.getEffectiveTime(), notNullValue());
    }
}
