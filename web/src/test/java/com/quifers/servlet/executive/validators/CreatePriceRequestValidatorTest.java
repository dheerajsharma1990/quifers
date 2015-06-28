package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.OrderId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.request.executive.CreatePriceRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CreatePriceRequestValidatorTest {

    private final CreatePriceRequestValidator validator = new CreatePriceRequestValidator();

    @Test
    public void shouldPassAllValidations() throws Exception {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("QUIFID");
        when(request.getParameter("distance")).thenReturn("25");
        when(request.getParameter("waiting_minutes")).thenReturn("30");
        when(request.getParameter("pick_up_floors")).thenReturn("5");
        when(request.getParameter("pick_up_lift_working")).thenReturn("true");
        when(request.getParameter("drop_off_floors")).thenReturn("3");
        when(request.getParameter("drop_off_lift_working")).thenReturn("false");

        //when
        CreatePriceRequest createPriceRequest = validator.validateRequest(request);

        //then
        assertThat(createPriceRequest.getOrderId(), is(new OrderId("QUIFID")));
        assertThat(createPriceRequest.getDistance(), is(25));
        assertThat(createPriceRequest.getWaitingMinutes(), is(30));
        assertThat(createPriceRequest.getPickupFloors(), is(5));
        assertThat(createPriceRequest.isPickupLiftWorking(), is(true));
        assertThat(createPriceRequest.getDropOffFloors(), is(3));
        assertThat(createPriceRequest.isDropOffLiftWorking(), is(false));

    }

    @Test
    public void shouldFailForEmptyOrderIdValidations() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("");
        when(request.getParameter("distance")).thenReturn("25");
        when(request.getParameter("waiting_minutes")).thenReturn("30");
        when(request.getParameter("pick_up_floors")).thenReturn("5");
        when(request.getParameter("pick_up_lift_working")).thenReturn("true");
        when(request.getParameter("drop_off_floors")).thenReturn("3");
        when(request.getParameter("drop_off_lift_working")).thenReturn("false");

        //when
        try {
            validator.validateRequest(request);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }

    }

    @Test
    public void shouldFailForEmptyDistanceValidations() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("QUIFID");
        when(request.getParameter("waiting_minutes")).thenReturn("30");
        when(request.getParameter("pick_up_floors")).thenReturn("5");
        when(request.getParameter("pick_up_lift_working")).thenReturn("true");
        when(request.getParameter("drop_off_floors")).thenReturn("3");
        when(request.getParameter("drop_off_lift_working")).thenReturn("false");

        //when
        try {
            validator.validateRequest(request);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }

    }

    @Test
    public void shouldFailForInvalidWaitingMinutesValidations() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("QUIFID");
        when(request.getParameter("distance")).thenReturn("25");
        when(request.getParameter("waiting_minutes")).thenReturn("-30");
        when(request.getParameter("pick_up_floors")).thenReturn("5");
        when(request.getParameter("pick_up_lift_working")).thenReturn("true");
        when(request.getParameter("drop_off_floors")).thenReturn("3");
        when(request.getParameter("drop_off_lift_working")).thenReturn("false");

        //when
        try {
            validator.validateRequest(request);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailForEmptyPickUpFloorsValidations() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("QUIFID");
        when(request.getParameter("distance")).thenReturn("25");
        when(request.getParameter("waiting_minutes")).thenReturn("-30");
        when(request.getParameter("pick_up_lift_working")).thenReturn("true");
        when(request.getParameter("drop_off_floors")).thenReturn("3");
        when(request.getParameter("drop_off_lift_working")).thenReturn("false");

        //when
        try {
            validator.validateRequest(request);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailForInvalidPickUpLiftWorkingValidations() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("QUIFID");
        when(request.getParameter("distance")).thenReturn("25");
        when(request.getParameter("waiting_minutes")).thenReturn("30");
        when(request.getParameter("pick_up_floors")).thenReturn("5");
        when(request.getParameter("pick_up_lift_working")).thenReturn("truee");
        when(request.getParameter("drop_off_floors")).thenReturn("3");
        when(request.getParameter("drop_off_lift_working")).thenReturn("false");

        //when
        try {
            validator.validateRequest(request);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailForInvalidDropOffFloorsValidations() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("QUIFID");
        when(request.getParameter("distance")).thenReturn("25");
        when(request.getParameter("waiting_minutes")).thenReturn("-30");
        when(request.getParameter("pick_up_floors")).thenReturn("5");
        when(request.getParameter("pick_up_lift_working")).thenReturn("true");
        when(request.getParameter("drop_off_floors")).thenReturn("3.5");
        when(request.getParameter("drop_off_lift_working")).thenReturn("false");

        //when
        try {
            validator.validateRequest(request);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailForEmptyDropOffLiftWorkingValidations() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("order_id")).thenReturn("QUIFID");
        when(request.getParameter("distance")).thenReturn("25");
        when(request.getParameter("waiting_minutes")).thenReturn("30");
        when(request.getParameter("pick_up_floors")).thenReturn("5");
        when(request.getParameter("pick_up_lift_working")).thenReturn("truee");
        when(request.getParameter("drop_off_floors")).thenReturn("3");

        //when
        try {
            validator.validateRequest(request);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }
}
