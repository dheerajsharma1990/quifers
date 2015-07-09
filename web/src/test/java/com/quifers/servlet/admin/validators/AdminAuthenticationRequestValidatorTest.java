package com.quifers.servlet.admin.validators;

import com.quifers.domain.id.AdminId;
import com.quifers.request.admin.AdminAuthenticationRequest;
import com.quifers.validations.EmptyStringAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.StringLengthAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AdminAuthenticationRequestValidatorTest {

    private final StringLengthAttributeValidator userIdAttributeValidator = mock(StringLengthAttributeValidator.class);
    private final EmptyStringAttributeValidator emptyStringAttributeValidator = mock(EmptyStringAttributeValidator.class);

    private final AdminAuthenticationRequestValidator validator = new AdminAuthenticationRequestValidator(userIdAttributeValidator, emptyStringAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        String userId = "userId";
        String accessToken = "accessToken";
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn(userId);
        when(servletRequest.getParameter("access_token")).thenReturn(accessToken);
        when(userIdAttributeValidator.validate(userId)).thenReturn(userId);
        when(emptyStringAttributeValidator.validate(accessToken)).thenReturn(accessToken);

        //when
        AdminAuthenticationRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(userId);
        verify(emptyStringAttributeValidator, times(1)).validate(accessToken);
        assertThat(request.getAdminId(), is(new AdminId(userId)));
        assertThat(request.getAccessToken(), is(accessToken));
    }
}
