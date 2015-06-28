package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.request.executive.FieldExecutiveAuthenticationRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldExecutiveAuthenticationRequestValidatorTest {

    private final FieldExecutiveAuthenticationRequestValidator validator = new FieldExecutiveAuthenticationRequestValidator();

    @Test
    public void shouldPassAllValidations() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String fieldExecutiveId = "fieldExecutiveId";
        String accessToken = "accessToken";
        when(servletRequest.getParameter("user_id")).thenReturn(fieldExecutiveId);
        when(servletRequest.getParameter("access_token")).thenReturn(accessToken);

        //when
        FieldExecutiveAuthenticationRequest fieldExecutiveAuthenticationRequest = validator.validateRequest(servletRequest);

        //then
        assertThat(fieldExecutiveAuthenticationRequest.getFieldExecutiveId(), is(new FieldExecutiveId(fieldExecutiveId)));
        assertThat(fieldExecutiveAuthenticationRequest.getAccessToken(), is(accessToken));
    }

    @Test
    public void shouldFailOnEmptyFieldExecutiveIdValidations() {
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
