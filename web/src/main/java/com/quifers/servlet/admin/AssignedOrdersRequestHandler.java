package com.quifers.servlet.admin;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.servlet.RequestHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.quifers.response.Responses.getOrderResponse;

public class AssignedOrdersRequestHandler implements RequestHandler {

    private final OrderDao orderDao;

    public AssignedOrdersRequestHandler(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException {
        Collection<Order> assignedOrders = orderDao.getAssignedOrders();
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getOrderResponse(assignedOrders));
    }
}
