package com.quifers.servlet.executive.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.executive.validators.CreatePriceRequestValidator;
import com.quifers.servlet.listener.WebPublisher;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class FieldExecutiveRequestHandlerFactory {

    private final OrderDao orderDao;
    private final WebPublisher webPublisher;

    public FieldExecutiveRequestHandlerFactory(OrderDao orderDao, WebPublisher webPublisher) {
        this.orderDao = orderDao;
        this.webPublisher = webPublisher;
    }


    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String requestURI = servletRequest.getRequestURI();
        if (isEqual("/api/v0/executive/order/create/price", requestURI)) {
            return new CreatePriceRequestHandler(new CreatePriceRequestValidator(), orderDao, webPublisher);
        }
        throw new CommandNotFoundException(requestURI);
    }

}
