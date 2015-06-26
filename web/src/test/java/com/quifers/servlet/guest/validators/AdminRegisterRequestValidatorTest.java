package com.quifers.servlet.guest.validators;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.guest.request.AdminRegisterRequest;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class AdminRegisterRequestValidatorTest {

    private final AdminRegisterRequestValidator validator = new AdminRegisterRequestValidator();

    @Test
    public void shouldPassAllValidation() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn("myAdminId");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");
        when(servletRequest.getParameter("name")).thenReturn("My Name");
        when(servletRequest.getParameter("mobile_number")).thenReturn("9811982244");

        //when
        AdminRegisterRequest request = validator.validateRequest(servletRequest);

        //then
        assertThat(request.getAdmin(), is(new Admin(new AdminId("myAdminId"), "My Name", 9811982244l)));
        assertThat(request.getAdminAccount(), is(new AdminAccount(new AdminId("myAdminId"), "funtoooosh")));
    }

    @Test
    public void shouldFailEmptyAdminIdValidation() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn("  ");
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
        when(servletRequest.getParameter("user_id")).thenReturn("myAdminId");
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
        when(servletRequest.getParameter("user_id")).thenReturn("myAdminId");
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
        when(servletRequest.getParameter("user_id")).thenReturn("myAdminId");
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
