package com.quifers.servlet.guest.handlers;

import com.quifers.dao.AdminAccountDao;
import com.quifers.dao.AdminDao;
import com.quifers.dao.OrderDao;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.guest.validators.AdminRegisterRequestValidator;
import com.quifers.servlet.guest.validators.NewOrderRequestValidator;
import com.quifers.servlet.listener.WebPublisher;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class GuestRequestHandlerFactory {

    private final AdminAccountDao adminAccountDao;
    private final AdminDao adminDao;
    private final OrderIdGeneratorService orderIdGeneratorService;
    private final OrderDao orderDao;
    private final WebPublisher webPublisher;

    public GuestRequestHandlerFactory(AdminAccountDao adminAccountDao, AdminDao adminDao, OrderIdGeneratorService orderIdGeneratorService, OrderDao orderDao, WebPublisher webPublisher) {
        this.adminAccountDao = adminAccountDao;
        this.adminDao = adminDao;
        this.orderIdGeneratorService = orderIdGeneratorService;
        this.orderDao = orderDao;
        this.webPublisher = webPublisher;
    }

    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String requestURI = servletRequest.getRequestURI();
        if (isEqual("/api/v0/guest/admin/register", requestURI)) {
            return new AdminRegisterRequestHandler(new AdminRegisterRequestValidator(), adminAccountDao, adminDao);
        } else if (isEqual("/api/v0/guest/order/book", requestURI)) {
            return new NewOrderRequestHandler(new NewOrderRequestValidator(orderIdGeneratorService), orderDao, webPublisher);
        }
        throw new CommandNotFoundException(requestURI);
    }
}
