package com.quifers.servlet.admin.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.enums.OrderState;
import com.quifers.validations.InvalidRequestException;
import com.quifers.servlet.RequestHandler;
import com.quifers.request.admin.BookingDateRangeRequest;
import com.quifers.servlet.admin.validators.BookingDateRangeRequestValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.quifers.response.Responses.getOrderResponse;

public class CompletedOrdersRequestHandler implements RequestHandler {

    private final BookingDateRangeRequestValidator requestValidator;
    private final OrderDao orderDao;

    public CompletedOrdersRequestHandler(BookingDateRangeRequestValidator requestValidator, OrderDao orderDao) {
        this.requestValidator = requestValidator;
        this.orderDao = orderDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws ServletException, IOException, InvalidRequestException {
        BookingDateRangeRequest request = requestValidator.validateRequest(servletRequest);
        Collection<Order> assignedOrders = orderDao.getOrders(OrderState.COMPLETED, request.getBeginBookingDay(), request.getEndBookingDay());
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getOrderResponse(assignedOrders));
    }
}
