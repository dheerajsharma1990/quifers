package com.quifers.servlet.executive.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.servlet.RequestHandler;
import com.quifers.request.executive.ReceivableRequest;
import com.quifers.servlet.executive.validators.ReceivableRequestValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.quifers.response.Responses.getReceivableResponse;

public class ReceivableRequestHandler implements RequestHandler {

    private final ReceivableRequestValidator receivableRequestValidator;
    private final OrderDao orderDao;

    public ReceivableRequestHandler(ReceivableRequestValidator receivableRequestValidator, OrderDao orderDao) {
        this.receivableRequestValidator = receivableRequestValidator;
        this.orderDao = orderDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        ReceivableRequest receivableRequest = receivableRequestValidator.validateRequest(servletRequest);
        Order order = orderDao.getOrder(receivableRequest.getOrderId());
        order.setReceivables(receivableRequest.getReceivables());
        orderDao.updateOrder(order);
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getReceivableResponse(order));
    }
}
