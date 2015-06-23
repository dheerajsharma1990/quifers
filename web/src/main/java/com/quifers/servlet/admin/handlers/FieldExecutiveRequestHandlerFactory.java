package com.quifers.servlet.admin.handlers;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.admin.validators.AssignFieldExecutiveRequestValidator;
import com.quifers.servlet.admin.validators.FieldExecutiveRegisterRequestValidator;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class FieldExecutiveRequestHandlerFactory {

    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private final FieldExecutiveDao fieldExecutiveDao;
    private final OrderDao orderDao;

    public FieldExecutiveRequestHandlerFactory(FieldExecutiveAccountDao fieldExecutiveAccountDao, FieldExecutiveDao fieldExecutiveDao, OrderDao orderDao) {
        this.fieldExecutiveAccountDao = fieldExecutiveAccountDao;
        this.fieldExecutiveDao = fieldExecutiveDao;
        this.orderDao = orderDao;
    }


    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String command = servletRequest.getParameter("cmd");
        if (isEqual("register", command)) {
            return new FieldExecutiveRegisterRequestHandler(new FieldExecutiveRegisterRequestValidator(), fieldExecutiveAccountDao, fieldExecutiveDao);
        } else if (isEqual("getAll", command)) {
            return new GetAllFieldExecutivesRequestHandler(fieldExecutiveDao);
        } else if (isEqual("assign", command)) {
            return new AssignFieldExecutiveRequestHandler(new AssignFieldExecutiveRequestValidator(), fieldExecutiveDao, orderDao);
        }
        throw new CommandNotFoundException(command);
    }

}
