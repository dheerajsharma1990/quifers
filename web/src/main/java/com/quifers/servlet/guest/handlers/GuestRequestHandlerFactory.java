package com.quifers.servlet.guest.handlers;

import com.quifers.dao.AdminAccountDao;
import com.quifers.dao.AdminDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.guest.validators.AdminRegisterRequestValidator;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class GuestRequestHandlerFactory {

    private final AdminAccountDao adminAccountDao;
    private final AdminDao adminDao;

    public GuestRequestHandlerFactory(AdminAccountDao adminAccountDao, AdminDao adminDao) {
        this.adminAccountDao = adminAccountDao;
        this.adminDao = adminDao;
    }

    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String requestURI = servletRequest.getRequestURI();
        if (isEqual("/api/v0/guest/admin/register", requestURI)) {
            return new AdminRegisterRequestHandler(new AdminRegisterRequestValidator(), adminAccountDao, adminDao);
        }
        throw new CommandNotFoundException(requestURI);
    }
}
