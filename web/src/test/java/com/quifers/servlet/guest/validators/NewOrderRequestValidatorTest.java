package com.quifers.servlet.guest.validators;

import com.quifers.domain.Address;
import com.quifers.domain.Client;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import com.quifers.request.guest.NewOrderRequest;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.validations.*;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class NewOrderRequestValidatorTest {

    private final OrderIdGeneratorService orderIdGeneratorService = mock(OrderIdGeneratorService.class);
    private final MobileNumberAttributeValidator mobileNumberAttributeValidator = mock(MobileNumberAttributeValidator.class);
    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);
    private final BooleanAttributeValidator booleanAttributeValidator = mock(BooleanAttributeValidator.class);
    private final PositiveIntegerAttributeValidator positiveIntegerAttributeValidator = mock(PositiveIntegerAttributeValidator.class);
    private final DateAttributeValidator dateAttributeValidator = mock(DateAttributeValidator.class);
    private final NewOrderRequestValidator validator = new NewOrderRequestValidator(orderIdGeneratorService, mobileNumberAttributeValidator, emptyStringAttributeValidator,
            booleanAttributeValidator, positiveIntegerAttributeValidator, dateAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("name_label")).thenReturn("name");
        when(servletRequest.getParameter("number_label")).thenReturn("99999888877");
        when(servletRequest.getParameter("emailid")).thenReturn("some@email.com");
        when(servletRequest.getParameter("date_time_label")).thenReturn("22/09/2015 14:15");
        when(servletRequest.getParameter("vehicle_list_label")).thenReturn("Vehicle");
        when(servletRequest.getParameter("house_no_pick")).thenReturn("12");
        when(servletRequest.getParameter("society_name_pick")).thenReturn("society_pk");
        when(servletRequest.getParameter("area_pick")).thenReturn("area_pk");
        when(servletRequest.getParameter("city_pick")).thenReturn("city_pk");
        when(servletRequest.getParameter("house_no_drop")).thenReturn("21");
        when(servletRequest.getParameter("society_name_drop")).thenReturn("society_dp");
        when(servletRequest.getParameter("area_drop")).thenReturn("area_dp");
        when(servletRequest.getParameter("city_drop")).thenReturn("city_dp");
        when(servletRequest.getParameter("labour")).thenReturn("1");
        when(servletRequest.getParameter("floor_no_pick")).thenReturn("3");
        when(servletRequest.getParameter("lift_pickup")).thenReturn("false");
        when(servletRequest.getParameter("floor_no_drop")).thenReturn("4");
        when(servletRequest.getParameter("lift_drop")).thenReturn("true");

        when(orderIdGeneratorService.getNewOrderId()).thenReturn("QUIFID111");
        when(emptyStringAttributeValidator.validate("name")).thenReturn("name");
        when(mobileNumberAttributeValidator.validate("99999888877")).thenReturn(99999888877l);
        when(emptyStringAttributeValidator.validate("some@email.com")).thenReturn("some@email.com");
        Calendar calendar = Calendar.getInstance();
        calendar.set(2015, 8, 22, 14, 15, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        when(dateAttributeValidator.validate("22/09/2015 14:15")).thenReturn(calendar.getTime());
        when(emptyStringAttributeValidator.validate("Vehicle")).thenReturn("Vehicle");
        when(emptyStringAttributeValidator.validate("12")).thenReturn("12");
        when(emptyStringAttributeValidator.validate("society_pk")).thenReturn("society_pk");
        when(emptyStringAttributeValidator.validate("area_pk")).thenReturn("area_pk");
        when(emptyStringAttributeValidator.validate("city_pk")).thenReturn("city_pk");
        when(emptyStringAttributeValidator.validate("21")).thenReturn("21");
        when(emptyStringAttributeValidator.validate("society_dp")).thenReturn("society_dp");
        when(emptyStringAttributeValidator.validate("area_dp")).thenReturn("area_dp");
        when(emptyStringAttributeValidator.validate("city_dp")).thenReturn("city_dp");
        when(positiveIntegerAttributeValidator.validate("1")).thenReturn(1);
        when(positiveIntegerAttributeValidator.validate("3")).thenReturn(3);
        when(booleanAttributeValidator.validate("false")).thenReturn(false);
        when(positiveIntegerAttributeValidator.validate("4")).thenReturn(4);
        when(booleanAttributeValidator.validate("true")).thenReturn(true);

        //when
        NewOrderRequest request = validator.validateRequest(servletRequest);

        //then
        Order order = request.getOrder();
        assertThat(order, notNullValue());
        OrderId orderId = new OrderId("QUIFID111");
        assertThat(order.getOrderId(), is(orderId));
        Client client = order.getClient();
        assertThat(client, is(new Client(orderId, "name", 99999888877l, "some@email.com")));
        OrderWorkflow orderWorkflow = order.getWorkflow(OrderState.BOOKED);
        assertThat(orderWorkflow, is(new OrderWorkflow(orderId, OrderState.BOOKED, calendar.getTime(), true)));
        assertThat(order.getVehicle(), is("Vehicle"));
        assertThat(order.getPickUpAddress(), is(new Address(orderId, AddressType.PICKUP, "12", "society_pk", "area_pk", "city_pk")));
        assertThat(order.getDropOffAddress(), is(new Address(orderId, AddressType.DROP, "21", "society_dp", "area_dp", "city_dp")));
        assertThat(order.getLabours(), is(1));
        assertThat(order.getPickupFloors(), is(3));
        assertThat(order.isPickupLiftWorking(), is(false));
        assertThat(order.getDropOffFloors(), is(4));
        assertThat(order.isDropOffLiftWorking(), is(true));
    }

}
