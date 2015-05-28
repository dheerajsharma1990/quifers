package com.quifers.request.validators;

import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class AdminAccountRegisterRequestValidatorTest {

    private final AdminAccountRegisterRequestValidator validator = new AdminAccountRegisterRequestValidator();

    @Test
    public void shouldThrowInvalidRequestExceptionOnEmptyClientName() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("user_id")).thenReturn(null);

        //when
        try {
            validator.validateAdminAccountRequest(request);
            fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("User Id cannot be empty."));
        }
    }
}
