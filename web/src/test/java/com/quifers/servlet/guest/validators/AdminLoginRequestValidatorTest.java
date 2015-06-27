package com.quifers.servlet.guest.validators;

import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.guest.request.AdminLoginRequest;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class AdminLoginRequestValidatorTest {

    private final AdminLoginRequestValidator validator = new AdminLoginRequestValidator();

    @Test
    public void shouldPassAllValidation() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn("myAdminId");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");

        //when
        AdminLoginRequest request = validator.validateRequest(servletRequest);

        //then
        assertThat(request.getAdminAccount(), is(new AdminAccount(new AdminId("myAdminId"), "funtoooosh")));
    }

    @Test
    public void shouldFailEmptyAdminIdValidation() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn("  ");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");

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

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }
    }

}
