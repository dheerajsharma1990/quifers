package com.quifers.servlet.executive.handlers;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.executive.request.GetOrdersRequest;
import com.quifers.servlet.executive.validators.GetOrdersRequestValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

import static com.quifers.response.Responses.getOrderResponse;

public class GetOrdersRequestHandler implements RequestHandler {

    private final GetOrdersRequestValidator requestValidator;
    private final OrderDao orderDao;
    private final FieldExecutiveDao fieldExecutiveDao;

    public GetOrdersRequestHandler(GetOrdersRequestValidator requestValidator, OrderDao orderDao, FieldExecutiveDao fieldExecutiveDao) {
        this.requestValidator = requestValidator;
        this.orderDao = orderDao;
        this.fieldExecutiveDao = fieldExecutiveDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        GetOrdersRequest getOrdersRequest = requestValidator.validateRequest(servletRequest);
        FieldExecutive fieldExecutive = fieldExecutiveDao.getFieldExecutive(getOrdersRequest.getFieldExecutiveId());
        Collection<Order> orders = orderDao.getBookedOrders(fieldExecutive, getOrdersRequest.getBookingDate());
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getOrderResponse(orders));
    }
}
