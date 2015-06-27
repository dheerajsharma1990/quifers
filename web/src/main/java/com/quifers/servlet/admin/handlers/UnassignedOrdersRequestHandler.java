package com.quifers.servlet.admin.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.quifers.response.Responses.getOrderResponse;

public class UnassignedOrdersRequestHandler implements RequestHandler {

    private final OrderDao orderDao;

    public UnassignedOrdersRequestHandler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException, InvalidRequestException {
        Collection<Order> unassignedOrders = orderDao.getUnassignedOrders();
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getOrderResponse(unassignedOrders));
    }
}
