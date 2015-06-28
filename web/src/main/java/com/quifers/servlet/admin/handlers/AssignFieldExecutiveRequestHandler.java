package com.quifers.servlet.admin.handlers;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.servlet.RequestHandler;
import com.quifers.request.admin.AssignFieldExecutiveRequest;
import com.quifers.servlet.admin.validators.AssignFieldExecutiveRequestValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.quifers.response.FieldExecutiveResponse.getSuccessResponse;

public class AssignFieldExecutiveRequestHandler implements RequestHandler {

    private final AssignFieldExecutiveRequestValidator requestValidator;
    private final FieldExecutiveDao fieldExecutiveDao;
    private final OrderDao orderDao;

    public AssignFieldExecutiveRequestHandler(AssignFieldExecutiveRequestValidator requestValidator, FieldExecutiveDao fieldExecutiveDao, OrderDao orderDao) {
        this.requestValidator = requestValidator;
        this.fieldExecutiveDao = fieldExecutiveDao;
        this.orderDao = orderDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        AssignFieldExecutiveRequest request = requestValidator.validateRequest(servletRequest);
        FieldExecutive fieldExecutive = fieldExecutiveDao.getFieldExecutive(request.getFieldExecutiveId());
        orderDao.assignFieldExecutive(request.getOrderId(), fieldExecutive);
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(getSuccessResponse());
    }
}
