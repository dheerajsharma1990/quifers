package com.quifers.servlet.admin;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class FieldExecutiveRequestHandlerFactory {

    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private final FieldExecutiveDao fieldExecutiveDao;

    public FieldExecutiveRequestHandlerFactory(FieldExecutiveAccountDao fieldExecutiveAccountDao, FieldExecutiveDao fieldExecutiveDao) {
        this.fieldExecutiveAccountDao = fieldExecutiveAccountDao;
        this.fieldExecutiveDao = fieldExecutiveDao;
    }


    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String command = servletRequest.getParameter("cmd");
        if (isEqual("register", command)) {
            return new FieldExecutiveRegisterRequestHandler(new FieldExecutiveRegisterRequestValidator(), fieldExecutiveAccountDao, fieldExecutiveDao);
        }
        throw new CommandNotFoundException(command);
    }

}
