package com.quifers.servlet.admin.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.admin.validators.BookingDateRangeRequestValidator;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class OrderRequestHandlerFactory {

    private final OrderDao orderDao;

    public OrderRequestHandlerFactory(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String command = servletRequest.getParameter("cmd");
        if (isEqual("unassigned", command)) {
            return new UnassignedOrdersRequestHandler(orderDao);
        } else if (isEqual("assigned", command)) {
            return new AssignedOrdersRequestHandler(new BookingDateRangeRequestValidator(), orderDao);
        } else if (isEqual("completed", command)) {
            return new CompletedOrdersRequestHandler(new BookingDateRangeRequestValidator(), orderDao);
        }
        throw new CommandNotFoundException(command);
    }

}
