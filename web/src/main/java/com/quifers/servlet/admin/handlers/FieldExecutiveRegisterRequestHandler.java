package com.quifers.servlet.admin.handlers;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.servlet.RequestHandler;
import com.quifers.request.admin.FieldExecutiveRegisterRequest;
import com.quifers.servlet.admin.validators.FieldExecutiveRegisterRequestValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.quifers.response.FieldExecutiveResponse.getSuccessResponse;

public class FieldExecutiveRegisterRequestHandler implements RequestHandler {

    private final FieldExecutiveRegisterRequestValidator registerRequestValidator;
    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private final FieldExecutiveDao fieldExecutiveDao;

    public FieldExecutiveRegisterRequestHandler(FieldExecutiveRegisterRequestValidator registerRequestValidator, FieldExecutiveAccountDao fieldExecutiveAccountDao, FieldExecutiveDao fieldExecutiveDao) {
        this.registerRequestValidator = registerRequestValidator;
        this.fieldExecutiveAccountDao = fieldExecutiveAccountDao;
        this.fieldExecutiveDao = fieldExecutiveDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        FieldExecutiveRegisterRequest request = registerRequestValidator.validateRequest(servletRequest);
        fieldExecutiveAccountDao.saveFieldExecutiveAccount(request.getFieldExecutiveAccount());
        fieldExecutiveDao.saveFieldExecutive(request.getFieldExecutive());
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getSuccessResponse());
    }
}
