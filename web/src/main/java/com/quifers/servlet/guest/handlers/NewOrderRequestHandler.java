package com.quifers.servlet.guest.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.enums.OrderState;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.guest.request.NewOrderRequest;
import com.quifers.servlet.guest.validators.NewOrderRequestValidator;
import com.quifers.servlet.listener.WebPublisher;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class NewOrderRequestHandler implements RequestHandler {

    private final NewOrderRequestValidator requestValidator;
    private final OrderDao orderDao;
    private final WebPublisher webPublisher;

    public NewOrderRequestHandler(NewOrderRequestValidator requestValidator, OrderDao orderDao, WebPublisher webPublisher) {
        this.requestValidator = requestValidator;
        this.orderDao = orderDao;
        this.webPublisher = webPublisher;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        NewOrderRequest newOrderRequest = requestValidator.validateRequest(servletRequest);
        Order order = newOrderRequest.getOrder();
        orderDao.saveOrder(order);
        webPublisher.publishEmailMessage(EmailType.NEW_ORDER, order.getOrderId());
        JSONObject object = new JSONObject();
        object.put("success", "true");
        object.put("order_state", OrderState.BOOKED.name());
        object.put("order_id", order.getOrderId().getOrderId());
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(object.toString());
    }
}
