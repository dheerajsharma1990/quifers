package com.quifers.servlet;

import com.quifers.dao.OrderDao;
import com.quifers.servlet.admin.AssignedOrdersRequestHandler;
import com.quifers.servlet.admin.OrderRequestHandlerFactory;
import com.quifers.servlet.admin.UnassignedOrdersRequestHandler;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.testng.Assert.fail;

public class OrderRequestHandlerFactoryTest {

    private final OrderRequestHandlerFactory orderRequestHandlerFactory = new OrderRequestHandlerFactory(mock(OrderDao.class));

    @Test
    public void shouldReturnUnassignedRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("unassigned");

        //when
        RequestHandler requestHandler = orderRequestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof UnassignedOrdersRequestHandler, is(true));

    }

    @Test
    public void shouldReturnAssignedRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("assigned");

        //when
        RequestHandler requestHandler = orderRequestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof AssignedOrdersRequestHandler, is(true));

    }

    @Test
    public void shouldThrowExceptionOnInvalidCommand() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("abc");

        try {
            orderRequestHandlerFactory.getRequestHandler(servletRequest);
            fail("Should have thrown command not found exception.");
        } catch (CommandNotFoundException e) {

        }

    }
}
