package com.quifers.servlet.admin;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.admin.request.AssignFieldExecutiveRequest;
import com.quifers.servlet.admin.validators.AssignFieldExecutiveRequestValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class AssignFieldExecutiveRequestValidatorTest {

    private final AssignFieldExecutiveRequestValidator validator = new AssignFieldExecutiveRequestValidator();

    @Test
    public void shouldPassAllValidation() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("order_id")).thenReturn("QUIFID001");

        //when
        AssignFieldExecutiveRequest request = validator.validateRequest(servletRequest);

        //then
        assertThat(request.getFieldExecutiveId(), is(new FieldExecutiveId("myFieldExecutiveId")));
        assertThat(request.getOrderId(), is(new OrderId("QUIFID001")));
    }

    @Test
    public void shouldThrowExceptionOnNullFieldExecutiveId() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn(null);
        when(servletRequest.getParameter("order_id")).thenReturn("QUIFID001");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }

    }

    @Test
    public void shouldThrowExceptionOnNullOrderId() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("order_id")).thenReturn(null);

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }

    }

}