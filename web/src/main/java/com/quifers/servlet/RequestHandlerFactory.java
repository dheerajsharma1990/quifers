package com.quifers.servlet;

import com.quifers.dao.OrderDao;
import com.quifers.servlet.admin.UnassignedOrdersRequestHandler;

import javax.servlet.http.HttpServletRequest;

public class RequestHandlerFactory {

    private final OrderDao orderDao;

    public RequestHandlerFactory(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String command = servletRequest.getParameter("cmd");
        if ("unassigned".equalsIgnoreCase(command)) {
            return new UnassignedOrdersRequestHandler(orderDao);
        }
        throw new CommandNotFoundException(command);
    }

}