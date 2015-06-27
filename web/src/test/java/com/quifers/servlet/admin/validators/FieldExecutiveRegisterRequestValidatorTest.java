package com.quifers.servlet.admin.validators;

import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.admin.request.FieldExecutiveRegisterRequest;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class FieldExecutiveRegisterRequestValidatorTest {

    private final FieldExecutiveRegisterRequestValidator validator = new FieldExecutiveRegisterRequestValidator();

    @Test
    public void shouldPassAllValidation() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");
        when(servletRequest.getParameter("name")).thenReturn("My Name");
        when(servletRequest.getParameter("mobile_number")).thenReturn("9811982244");

        //when
        FieldExecutiveRegisterRequest request = validator.validateRequest(servletRequest);

        //then
        assertThat(request.getFieldExecutiveId(), is("myFieldExecutiveId"));
        assertThat(request.getPassword(), is("funtoooosh"));
        assertThat(request.getName(), is("My Name"));
        assertThat(request.getMobileNumber(), is(9811982244l));
    }

    @Test
    public void shouldFailEmptyFieldExecutiveIdValidation() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn("  ");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");
        when(servletRequest.getParameter("name")).thenReturn("My Name");
        when(servletRequest.getParameter("mobile_number")).thenReturn("9811982244");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailEmptyPasswordValidation() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("password")).thenReturn("  ");
        when(servletRequest.getParameter("name")).thenReturn("My Name");
        when(servletRequest.getParameter("mobile_number")).thenReturn("9811982244");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailTooLongNameValidation() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");
        when(servletRequest.getParameter("name")).thenReturn("My name is very longggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggggg");
        when(servletRequest.getParameter("mobile_number")).thenReturn("9811982244");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailNonDigitMobileValidation() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");
        when(servletRequest.getParameter("name")).thenReturn("My Name");
        when(servletRequest.getParameter("mobile_number")).thenReturn("-981198224");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }
    }

}