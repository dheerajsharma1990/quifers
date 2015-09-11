package com.quifers.servlet.executive.handlers;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.enums.OrderState;
import com.quifers.response.GeneratePriceResponse;
import com.quifers.servlet.RequestHandler;
import com.quifers.request.executive.CreatePriceRequest;
import com.quifers.servlet.executive.validators.CreatePriceRequestValidator;
import com.quifers.servlet.listener.WebPublisher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreatePriceRequestHandler implements RequestHandler {

    private final CreatePriceRequestValidator requestValidator;
    private final OrderDao orderDao;
    private final WebPublisher webPublisher;

    public CreatePriceRequestHandler(CreatePriceRequestValidator requestValidator, OrderDao orderDao, WebPublisher webPublisher) {
        this.requestValidator = requestValidator;
        this.orderDao = orderDao;
        this.webPublisher = webPublisher;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        CreatePriceRequest createPriceRequest = requestValidator.validateRequest(servletRequest);
        Order order = orderDao.getOrder(createPriceRequest.getOrderId());
        order.setDistance(createPriceRequest.getDistance());
        order.setWaitingMinutes(createPriceRequest.getWaitingMinutes());
        order.getWorkflow(OrderState.BOOKED).setCurrentState(false);
        order.addOrderWorkflow(createPriceRequest.getOrderWorkflow());

        order.setPickupFloors(createPriceRequest.getPickupFloors());
        order.setPickupLiftWorking(createPriceRequest.isPickupLiftWorking());
        order.setDropOffFloors(createPriceRequest.getDropOffFloors());
        order.setDropOffLiftWorking(createPriceRequest.isDropOffLiftWorking());

        orderDao.updateOrder(order);
        webPublisher.publishEmailMessage(EmailType.BILL_DETAILS, order);
        GeneratePriceResponse priceResponse = new GeneratePriceResponse(servletResponse);
        priceResponse.writeResponse(order.getCost());
    }
}
