package com.quifers.servlet;

import com.quifers.dao.OrderDao;
import com.quifers.servlet.admin.AssignedOrdersRequestHandler;
import com.quifers.servlet.admin.UnassignedOrdersRequestHandler;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class RequestHandlerFactory {

    private final OrderDao orderDao;

    public RequestHandlerFactory(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String command = servletRequest.getParameter("cmd");
        if (isEqual("unassigned", command)) {
            return new UnassignedOrdersRequestHandler(orderDao);
        } else if (isEqual("assigned", command)) {
            return new AssignedOrdersRequestHandler(orderDao);
        }
        throw new CommandNotFoundException(command);
    }

}
