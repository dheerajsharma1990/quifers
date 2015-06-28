package com.quifers.servlet.guest.handlers;

import com.quifers.authentication.AdminAuthenticator;
import com.quifers.authentication.FieldExecutiveAuthenticator;
import com.quifers.dao.AdminAccountDao;
import com.quifers.dao.AdminDao;
import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.OrderDao;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.RequestHandlerFactory;
import com.quifers.servlet.guest.validators.AdminLoginRequestValidator;
import com.quifers.servlet.guest.validators.AdminRegisterRequestValidator;
import com.quifers.servlet.guest.validators.FieldExecutiveLoginRequestValidator;
import com.quifers.servlet.guest.validators.NewOrderRequestValidator;
import com.quifers.servlet.listener.WebPublisher;
import com.quifers.validations.PasswordAttributeValidator;
import com.quifers.validations.UserIdAttributeValidator;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class GuestRequestHandlerFactory implements RequestHandlerFactory {

    private final AdminAccountDao adminAccountDao;
    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private final AdminDao adminDao;
    private final OrderIdGeneratorService orderIdGeneratorService;
    private final OrderDao orderDao;
    private final WebPublisher webPublisher;

    public GuestRequestHandlerFactory(AdminAccountDao adminAccountDao, FieldExecutiveAccountDao fieldExecutiveAccountDao,
                                      AdminDao adminDao, OrderIdGeneratorService orderIdGeneratorService, OrderDao orderDao, WebPublisher webPublisher) {
        this.adminAccountDao = adminAccountDao;
        this.fieldExecutiveAccountDao = fieldExecutiveAccountDao;
        this.adminDao = adminDao;
        this.orderIdGeneratorService = orderIdGeneratorService;
        this.orderDao = orderDao;
        this.webPublisher = webPublisher;
    }

    @Override
    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String requestURI = servletRequest.getRequestURI();
        if (isEqual("/api/v0/guest/admin/register", requestURI)) {
            return new AdminRegisterRequestHandler(new AdminRegisterRequestValidator(), adminAccountDao, adminDao);
        } else if (isEqual("/api/v0/guest/order/book", requestURI)) {
            return new NewOrderRequestHandler(new NewOrderRequestValidator(orderIdGeneratorService), orderDao, webPublisher);
        } else if (isEqual("/api/v0/guest/admin/login", requestURI)) {
            return new AdminLoginRequestHandler(new AdminLoginRequestValidator(new UserIdAttributeValidator(), new PasswordAttributeValidator()), new AdminAuthenticator(adminAccountDao));
        } else if (isEqual("/api/v0/guest/executive/login", requestURI)) {
            return new FieldExecutiveLoginRequestHandler(new FieldExecutiveLoginRequestValidator(), new FieldExecutiveAuthenticator(fieldExecutiveAccountDao));
        }
        throw new CommandNotFoundException(requestURI);
    }
}
