package com.quifers.servlet.guest.validators;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.request.guest.AdminRegisterRequest;
import com.quifers.validations.*;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AdminRegisterRequestValidatorTest {

    private final UserIdAttributeValidator userIdAttributeValidator = mock(UserIdAttributeValidator.class);
    private final PasswordAttributeValidator passwordAttributeValidator = mock(PasswordAttributeValidator.class);
    private final StringLengthAttributeValidator stringLengthAttributeValidator = mock(StringLengthAttributeValidator.class);
    private final MobileNumberAttributeValidator mobileNumberAttributeValidator = mock(MobileNumberAttributeValidator.class);
    private final AdminRegisterRequestValidator validator = new AdminRegisterRequestValidator(userIdAttributeValidator, passwordAttributeValidator,
            stringLengthAttributeValidator, mobileNumberAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        AdminId adminId = new AdminId("adminId");
        String password = "herPassword";
        String name = "Admin Name";
        long mobileNumber = 9811981198l;

        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn(adminId.getUserId());
        when(servletRequest.getParameter("password")).thenReturn(password);
        when(servletRequest.getParameter("name")).thenReturn(name);
        when(servletRequest.getParameter("mobile_number")).thenReturn(valueOf(mobileNumber));

        when(userIdAttributeValidator.validate(adminId.getUserId())).thenReturn(adminId.getUserId());
        when(passwordAttributeValidator.validate(password)).thenReturn(password);
        when(stringLengthAttributeValidator.validate(name)).thenReturn(name);
        when(mobileNumberAttributeValidator.validate(valueOf(mobileNumber))).thenReturn(mobileNumber);

        //when
        AdminRegisterRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(adminId.getUserId());
        verify(passwordAttributeValidator, times(1)).validate(password);
        verify(stringLengthAttributeValidator, times(1)).validate(name);
        verify(mobileNumberAttributeValidator, times(1)).validate(valueOf(mobileNumber));
        assertThat(request.getAdminAccount(), is(new AdminAccount(adminId, password)));
        assertThat(request.getAdmin(), is(new Admin(adminId, name, mobileNumber)));
    }

}
