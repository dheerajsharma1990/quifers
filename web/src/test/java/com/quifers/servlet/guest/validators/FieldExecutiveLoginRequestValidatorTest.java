package com.quifers.servlet.guest.validators;

import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.guest.FieldExecutiveLoginRequest;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.PasswordAttributeValidator;
import com.quifers.validations.UserIdAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class FieldExecutiveLoginRequestValidatorTest {

    private final UserIdAttributeValidator userIdAttributeValidator = mock(UserIdAttributeValidator.class);
    private final PasswordAttributeValidator passwordAttributeValidator = mock(PasswordAttributeValidator.class);
    private final FieldExecutiveLoginRequestValidator validator = new FieldExecutiveLoginRequestValidator(userIdAttributeValidator, passwordAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId("fieldExecutiveId");
        String password = "password";
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn(fieldExecutiveId.getUserId());
        when(servletRequest.getParameter("password")).thenReturn(password);
        when(userIdAttributeValidator.validate(fieldExecutiveId.getUserId())).thenReturn(fieldExecutiveId.getUserId());
        when(passwordAttributeValidator.validate(password)).thenReturn(password);

        //when
        FieldExecutiveLoginRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(fieldExecutiveId.getUserId());
        verify(passwordAttributeValidator, times(1)).validate(password);
        assertThat(request.getFieldExecutiveAccount(), is(new FieldExecutiveAccount(fieldExecutiveId, password)));
    }

}
