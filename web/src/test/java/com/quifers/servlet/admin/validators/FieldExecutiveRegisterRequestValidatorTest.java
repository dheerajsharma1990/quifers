package com.quifers.servlet.admin.validators;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.admin.request.FieldExecutiveRegisterRequest;
import com.quifers.servlet.validations.*;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class FieldExecutiveRegisterRequestValidatorTest {

    private final UserIdAttributeValidator userIdAttributeValidator = mock(UserIdAttributeValidator.class);
    private final PasswordAttributeValidator passwordAttributeValidator = mock(PasswordAttributeValidator.class);
    private final NameAttributeValidator nameAttributeValidator = mock(NameAttributeValidator.class);
    private final MobileNumberAttributeValidator mobileNumberAttributeValidator = mock(MobileNumberAttributeValidator.class);
    private final FieldExecutiveRegisterRequestValidator validator = new FieldExecutiveRegisterRequestValidator(userIdAttributeValidator, passwordAttributeValidator,
            nameAttributeValidator, mobileNumberAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId("fieldExecutiveId");
        String password = "herPassword";
        String name = "FE Name";
        long mobileNumber = 9811981198l;

        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("field_executive_id")).thenReturn(fieldExecutiveId.getUserId());
        when(servletRequest.getParameter("password")).thenReturn(password);
        when(servletRequest.getParameter("name")).thenReturn(name);
        when(servletRequest.getParameter("mobile_number")).thenReturn(valueOf(mobileNumber));

        when(userIdAttributeValidator.validate(fieldExecutiveId.getUserId())).thenReturn(fieldExecutiveId.getUserId());
        when(passwordAttributeValidator.validate(password)).thenReturn(password);
        when(nameAttributeValidator.validate(name)).thenReturn(name);
        when(mobileNumberAttributeValidator.validate(valueOf(mobileNumber))).thenReturn(mobileNumber);

        //when
        FieldExecutiveRegisterRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(fieldExecutiveId.getUserId());
        verify(passwordAttributeValidator, times(1)).validate(password);
        verify(nameAttributeValidator, times(1)).validate(name);
        verify(mobileNumberAttributeValidator, times(1)).validate(valueOf(mobileNumber));
        assertThat(request.getFieldExecutiveAccount(), is(new FieldExecutiveAccount(fieldExecutiveId, password)));
        assertThat(request.getFieldExecutive(), is(new FieldExecutive(fieldExecutiveId, name,mobileNumber)));
    }

}