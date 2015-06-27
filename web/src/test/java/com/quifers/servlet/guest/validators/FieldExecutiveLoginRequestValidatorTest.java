package com.quifers.servlet.guest.validators;

import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.guest.request.FieldExecutiveLoginRequest;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class FieldExecutiveLoginRequestValidatorTest {

    private final FieldExecutiveLoginRequestValidator validator = new FieldExecutiveLoginRequestValidator();

    @Test
    public void shouldPassAllValidation() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("password")).thenReturn("funtoooosh");

        //when
        FieldExecutiveLoginRequest request = validator.validateRequest(servletRequest);

        //then
        assertThat(request.getFieldExecutiveAccount(), is(new FieldExecutiveAccount(new FieldExecutiveId("myFieldExecutiveId"), "funtoooosh")));
    }

    @Test
    public void shouldFailEmptyFieldExecutiveIdValidation() {
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
        when(servletRequest.getParameter("user_id")).thenReturn("myFieldExecutiveId");
        when(servletRequest.getParameter("password")).thenReturn("  ");

        //when
        try {
            validator.validateRequest(servletRequest);
            fail();
        } catch (InvalidRequestException e) {
        }
    }

}
