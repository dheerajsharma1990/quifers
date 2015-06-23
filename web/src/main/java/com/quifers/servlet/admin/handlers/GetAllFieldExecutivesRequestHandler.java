package com.quifers.servlet.admin.handlers;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.response.FieldExecutiveResponse;
import com.quifers.servlet.RequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class GetAllFieldExecutivesRequestHandler implements RequestHandler {

    private final FieldExecutiveDao fieldExecutiveDao;

    public GetAllFieldExecutivesRequestHandler(FieldExecutiveDao fieldExecutiveDao) {
        this.fieldExecutiveDao = fieldExecutiveDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        Collection<FieldExecutive> allFieldExecutives = fieldExecutiveDao.getAllFieldExecutives();
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(FieldExecutiveResponse.getAllFieldExecutivesResponse(allFieldExecutives));
    }
}
