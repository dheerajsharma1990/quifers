package com.quifers.request.validators;

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
    public void shouldThrowInvalidRequestExceptionOnEmptyClientName() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("user_id")).thenReturn("user");
        when(request.getParameter("password")).thenReturn("password");
        when(request.getParameter("name")).thenReturn("Some Name");
        when(request.getParameter("mobile")).thenReturn(null);

        //when
        try {
            validator.validateAdminAccountRequest(request);
            fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Mobile number cannot be empty."));
        }
    }
}
