package com.quifers.servlet.admin.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.admin.request.CompletedOrdersRequest;
import com.quifers.servlet.admin.validators.CompletedOrdersRequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.quifers.response.Responses.getOrderResponse;

public class CompletedOrdersRequestHandler implements RequestHandler {

    private final CompletedOrdersRequestValidator requestValidator;
    private final OrderDao orderDao;

    public CompletedOrdersRequestHandler(CompletedOrdersRequestValidator requestValidator, OrderDao orderDao) {
        this.requestValidator = requestValidator;
        this.orderDao = orderDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException, InvalidRequestException {
        CompletedOrdersRequest request = requestValidator.validateRequest(servletRequest);
        Collection<Order> assignedOrders = orderDao.getCompletedOrders(request.getBeginBookingDate(), request.getEndBookingDate());
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getOrderResponse(assignedOrders));
    }
}
