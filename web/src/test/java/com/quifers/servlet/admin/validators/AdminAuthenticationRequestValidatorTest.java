package com.quifers.servlet.admin.validators;

import com.quifers.domain.id.AdminId;
import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.admin.request.AdminAuthenticationRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminAuthenticationRequestValidatorTest {

    private final AdminAuthenticationRequestValidator validator = new AdminAuthenticationRequestValidator();

    @Test
    public void shouldPassAllValidations() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String adminId = "adminId";
        String accessToken = "accessToken";
        when(servletRequest.getParameter("user_id")).thenReturn(adminId);
        when(servletRequest.getParameter("access_token")).thenReturn(accessToken);

        //when
        AdminAuthenticationRequest adminAuthenticationRequest = validator.validateRequest(servletRequest);

        //then
        assertThat(adminAuthenticationRequest.getAdminId(), is(new AdminId(adminId)));
        assertThat(adminAuthenticationRequest.getAccessToken(), is(accessToken));
    }

    @Test
    public void shouldFailOnEmptyAdminIdValidations() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String accessToken = "accessToken";
        when(servletRequest.getParameter("access_token")).thenReturn(accessToken);

        //when
        try {
            validator.validateRequest(servletRequest);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailOnEmptyAccessTokenValidations() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String accessToken = "";
        when(servletRequest.getParameter("access_token")).thenReturn(accessToken);

        //when
        try {
            validator.validateRequest(servletRequest);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }
}
