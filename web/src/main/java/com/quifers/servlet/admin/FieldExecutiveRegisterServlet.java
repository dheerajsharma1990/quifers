package com.quifers.servlet.admin;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.FieldExecutiveRegisterRequest;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.response.FieldExecutiveResponse.getSuccessResponse;
import static com.quifers.servlet.listener.StartupContextListener.FIELD_EXECUTIVE_ACCOUNT_DAO;
import static com.quifers.servlet.listener.StartupContextListener.FIELD_EXECUTIVE_DAO;
import static java.lang.Long.valueOf;

public class FieldExecutiveRegisterServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveRegisterServlet.class);
    private FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private FieldExecutiveDao fieldExecutiveDao;

    @Override
    public void init() throws ServletException {
        fieldExecutiveAccountDao = (FieldExecutiveAccountDao) getServletContext().getAttribute(FIELD_EXECUTIVE_ACCOUNT_DAO);
        fieldExecutiveDao = (FieldExecutiveDao) getServletContext().getAttribute(FIELD_EXECUTIVE_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            FieldExecutiveRegisterRequest registerRequest = new FieldExecutiveRegisterRequest(request);
            fieldExecutiveAccountDao.saveFieldExecutiveAccount(new FieldExecutiveAccount(new FieldExecutiveId(registerRequest.getFieldExecutiveId()), registerRequest.getPassword()));
            fieldExecutiveDao.saveFieldExecutive(new FieldExecutive(new FieldExecutiveId(registerRequest.getFieldExecutiveId()), registerRequest.getName(), valueOf(registerRequest.getMobileNumber())));
            String successResponse = getSuccessResponse();
            response.setContentType("application/json");
            response.getWriter().write(successResponse);
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Throwable e) {
            LOGGER.error("Error occurred in registering field executive account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
