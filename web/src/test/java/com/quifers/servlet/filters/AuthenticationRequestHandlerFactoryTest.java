package com.quifers.servlet.filters;

import com.quifers.servlet.ApiGroup;
import com.quifers.servlet.CommandNotFoundException;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;

public class AuthenticationRequestHandlerFactoryTest {

    private final AuthenticationRequestHandlerFactory requestHandlerFactory = new AuthenticationRequestHandlerFactory();

    @Test
    public void shouldReturnAdminAuthenticationRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn(ApiGroup.ADMIN.getPath());

        //when
        AuthenticationRequestHandler requestHandler = requestHandlerFactory.getAuthenticationRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getRequestURI();
        assertThat(requestHandler instanceof AdminAuthenticationRequestHandler, is(true));
    }

    @Test
    public void shouldReturnFieldExecutiveAuthenticationRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn(ApiGroup.FIELD_EXECUTIVE.getPath());

        //when
        AuthenticationRequestHandler requestHandler = requestHandlerFactory.getAuthenticationRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getRequestURI();
        assertThat(requestHandler instanceof FieldExecutiveAuthenticationRequestHandler, is(true));
    }

    @Test
    public void shouldReturnGuestAuthenticationRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn(ApiGroup.GUEST.getPath());

        //when
        AuthenticationRequestHandler requestHandler = requestHandlerFactory.getAuthenticationRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getRequestURI();
        assertThat(requestHandler instanceof NoAuthenticationRequestHandler, is(true));
    }
}
