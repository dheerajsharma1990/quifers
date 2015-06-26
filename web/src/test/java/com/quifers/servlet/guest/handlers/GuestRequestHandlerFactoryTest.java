package com.quifers.servlet.guest.handlers;

import com.quifers.dao.AdminAccountDao;
import com.quifers.dao.AdminDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GuestRequestHandlerFactoryTest {

    private final GuestRequestHandlerFactory guestRequestHandlerFactory = new GuestRequestHandlerFactory(mock(AdminAccountDao.class),
            mock(AdminDao.class));

    @Test
    public void shouldReturnAdminRegisterRequestHandler() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn("/api/v0/guest/admin/register");

        //when
        RequestHandler requestHandler = guestRequestHandlerFactory.getRequestHandler(servletRequest);

        //then
        assertThat(requestHandler instanceof AdminRegisterRequestHandler, is(true));
    }

    @Test
    public void shouldThrowCommandNotFoundExceptionOnInvalidRequest() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn("blahblah");

        //when
        try {
            guestRequestHandlerFactory.getRequestHandler(servletRequest);
            Assert.fail();
        } catch (CommandNotFoundException e) {
        }

    }
}
