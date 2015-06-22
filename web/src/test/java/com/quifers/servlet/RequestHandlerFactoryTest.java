package com.quifers.servlet;

import com.quifers.dao.OrderDao;
import com.quifers.servlet.admin.UnassignedOrdersRequestHandler;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class RequestHandlerFactoryTest {

    private final RequestHandlerFactory requestHandlerFactory = new RequestHandlerFactory(mock(OrderDao.class));

    @Test
    public void shouldReturnCorrectHandlerFactory() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("unassigned");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        assertThat(requestHandler instanceof UnassignedOrdersRequestHandler, is(true));

    }

    @Test
    public void shouldReturnCorrectHandlerCaseInsensitiveFactory() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("Unassigned");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        assertThat(requestHandler instanceof UnassignedOrdersRequestHandler, is(true));

    }

    @Test
    public void shouldThrowExceptionOnInvalidCommand() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("abc");

        try {
            requestHandlerFactory.getRequestHandler(servletRequest);
            fail("Should have thrown command not found exception.");
        } catch (CommandNotFoundException e) {

        }

    }
}
