package com.quifers.servlet.executive.handlers;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.RequestHandlerFactory;
import com.quifers.servlet.executive.validators.CreatePriceRequestValidator;
import com.quifers.servlet.executive.validators.GetOrdersRequestValidator;
import com.quifers.servlet.executive.validators.ReceivableRequestValidator;
import com.quifers.servlet.listener.WebPublisher;
import com.quifers.validations.*;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class FieldExecutiveRequestHandlerFactory implements RequestHandlerFactory {

    private final OrderDao orderDao;
    private final FieldExecutiveDao fieldExecutiveDao;
    private final WebPublisher webPublisher;

    public FieldExecutiveRequestHandlerFactory(OrderDao orderDao, FieldExecutiveDao fieldExecutiveDao, WebPublisher webPublisher) {
        this.orderDao = orderDao;
        this.fieldExecutiveDao = fieldExecutiveDao;
        this.webPublisher = webPublisher;
    }

    @Override
    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String requestURI = servletRequest.getRequestURI();
        if (isEqual("/api/v0/executive/order/create/price", requestURI)) {
            return new CreatePriceRequestHandler(new CreatePriceRequestValidator(new OrderIdAttributeValidator(), new PositiveIntegerAttributeValidator(), new BooleanAttributeValidator()), orderDao, webPublisher);
        } else if (isEqual("/api/v0/executive/order/get/all", requestURI)) {
            return new GetOrdersRequestHandler(new GetOrdersRequestValidator(new UserIdAttributeValidator(), new DayAttributeValidator()), orderDao, fieldExecutiveDao);
        } else if (isEqual("/api/v0/executive/order/receivables", requestURI)) {
            return new ReceivableRequestHandler(new ReceivableRequestValidator(new OrderIdAttributeValidator(), new PositiveIntegerAttributeValidator()), orderDao);
        }
        throw new CommandNotFoundException(requestURI);
    }

}
