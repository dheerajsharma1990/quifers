package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.executive.FieldExecutiveAuthenticationRequest;
import com.quifers.validations.AccessTokenAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.UserIdAttributeValidator;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class FieldExecutiveAuthenticationRequestValidatorTest {

    private final UserIdAttributeValidator userIdAttributeValidator = mock(UserIdAttributeValidator.class);
    private final AccessTokenAttributeValidator accessTokenAttributeValidator = mock(AccessTokenAttributeValidator.class);

    private final FieldExecutiveAuthenticationRequestValidator validator = new FieldExecutiveAuthenticationRequestValidator(userIdAttributeValidator, accessTokenAttributeValidator);

    @Test
    public void shouldCallAllValidations() throws InvalidRequestException {
        //given
        String userId = "userId";
        String accessToken = "accessToken";
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("user_id")).thenReturn(userId);
        when(servletRequest.getParameter("access_token")).thenReturn(accessToken);
        when(userIdAttributeValidator.validate(userId)).thenReturn(userId);
        when(accessTokenAttributeValidator.validate(accessToken)).thenReturn(accessToken);

        //when
        FieldExecutiveAuthenticationRequest request = validator.validateRequest(servletRequest);

        //then
        verify(userIdAttributeValidator, times(1)).validate(userId);
        verify(accessTokenAttributeValidator, times(1)).validate(accessToken);
        assertThat(request.getFieldExecutiveId(), is(new FieldExecutiveId(userId)));
        assertThat(request.getAccessToken(), is(accessToken));
    }
}
