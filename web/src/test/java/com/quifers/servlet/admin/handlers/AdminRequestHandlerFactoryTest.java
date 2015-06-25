package com.quifers.servlet.admin.handlers;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.testng.Assert.fail;

public class AdminRequestHandlerFactoryTest {

    private final AdminRequestHandlerFactory requestHandlerFactory = new AdminRequestHandlerFactory(mock(FieldExecutiveAccountDao.class),
            mock(FieldExecutiveDao.class), mock(OrderDao.class));

    @Test
    public void shouldReturnRegisterFieldExecutiveRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("registerFieldExecutive");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof FieldExecutiveRegisterRequestHandler, is(true));

    }

    @Test
    public void shouldReturnGetAllFieldExecutiveRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("getAllFieldExecutives");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof GetAllFieldExecutivesRequestHandler, is(true));

    }

    @Test
    public void shouldReturnAssignFieldExecutiveRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("assignFieldExecutive");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof AssignFieldExecutiveRequestHandler, is(true));

    }

    @Test
    public void shouldReturnUnassignedOrdersRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("unassignedOrders");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof UnassignedOrdersRequestHandler, is(true));

    }

    @Test
    public void shouldReturnAssignedOrdersRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("assignedOrders");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof AssignedOrdersRequestHandler, is(true));

    }

    @Test
    public void shouldReturnCompletedOrdersRequestHandler() throws CommandNotFoundException {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getParameter("cmd")).thenReturn("completedOrders");

        //when
        RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(servletRequest);

        //then
        verify(servletRequest, times(1)).getParameter("cmd");
        assertThat(requestHandler instanceof CompletedOrdersRequestHandler, is(true));

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
