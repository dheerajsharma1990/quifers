package com.quifers.servlet.executive.handlers;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.listener.WebPublisher;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class FieldExecutiveRequestHandlerFactoryTest {

    private final FieldExecutiveRequestHandlerFactory fieldExecutiveRequestHandlerFactory = new FieldExecutiveRequestHandlerFactory(mock(OrderDao.class), mock(FieldExecutiveDao.class), mock(WebPublisher.class));

    @Test
    public void shouldReturnCreatePriceRequestHandler() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn("/api/v0/executive/order/create/price");

        //when
        RequestHandler requestHandler = fieldExecutiveRequestHandlerFactory.getRequestHandler(servletRequest);

        //then
        assertThat(requestHandler instanceof CreatePriceRequestHandler, is(true));
    }

    @Test
    public void shouldReturnGetOrdersRequestHandler() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn("/api/v0/executive/order/get/all");
        //when
        RequestHandler requestHandler = fieldExecutiveRequestHandlerFactory.getRequestHandler(servletRequest);

        //then
        assertThat(requestHandler instanceof GetOrdersRequestHandler, is(true));
    }

    @Test
    public void shouldReturnReceivableRequestHandler() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn("/api/v0/executive/order/receivables");
        //when
        RequestHandler requestHandler = fieldExecutiveRequestHandlerFactory.getRequestHandler(servletRequest);

        //then
        assertThat(requestHandler instanceof ReceivableRequestHandler, is(true));
    }


    @Test
    public void shouldThrowCommandNotFoundExceptionOnInvalidRequest() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        when(servletRequest.getRequestURI()).thenReturn("blahblah");

        //when
        try {
            fieldExecutiveRequestHandlerFactory.getRequestHandler(servletRequest);
            Assert.fail();
        } catch (CommandNotFoundException e) {
        }

    }

}
