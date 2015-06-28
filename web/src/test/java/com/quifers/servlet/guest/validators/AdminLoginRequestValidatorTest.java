package com.quifers.servlet.guest.validators;

import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.request.guest.AdminLoginRequest;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.PasswordAttributeValidator;
import com.quifers.validations.UserIdAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AdminLoginRequestValidatorTest {

    private final UserIdAttributeValidator userIdAttributeValidator = mock(UserIdAttributeValidator.class);
    private final PasswordAttributeValidator passwordAttributeValidator = mock(PasswordAttributeValidator.class);
    private final AdminLoginRequestValidator validator = new AdminLoginRequestValidator(userIdAttributeValidator, passwordAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        AdminId adminId = new AdminId("adminId");
        String password = "password";
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn(adminId.getUserId());
        when(servletRequest.getParameter("password")).thenReturn(password);
        when(userIdAttributeValidator.validate(adminId.getUserId())).thenReturn(adminId.getUserId());
        when(passwordAttributeValidator.validate(password)).thenReturn(password);

        //when
        AdminLoginRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(adminId.getUserId());
        verify(passwordAttributeValidator, times(1)).validate(password);
        assertThat(request.getAdminAccount(), is(new AdminAccount(adminId, password)));
    }

}
