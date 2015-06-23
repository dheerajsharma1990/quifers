package com.quifers.servlet.admin;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.RequestHandler;

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
        FieldExecutiveId fieldExecutiveId = new FieldExecutiveId(request.getFieldExecutiveId());
        fieldExecutiveAccountDao.saveFieldExecutiveAccount(new FieldExecutiveAccount(fieldExecutiveId, request.getPassword()));
        fieldExecutiveDao.saveFieldExecutive(new FieldExecutive(fieldExecutiveId, request.getName(), request.getMobileNumber()));
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getSuccessResponse());
    }
}
