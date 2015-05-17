package com.quifers.servlet;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.quifers.listener.StartupContextListener.FIELD_EXECUTIVE_ACCOUNT_DAO;
import static com.quifers.listener.StartupContextListener.FIELD_EXECUTIVE_DAO;

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
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            long mobileNumber = Long.valueOf(request.getParameter("mobileNumber"));
            FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(userId, password);
            FieldExecutive fieldExecutive = new FieldExecutive(userId, name, mobileNumber);
            fieldExecutiveAccountDao.saveAccount(fieldExecutiveAccount);
            fieldExecutiveDao.saveFieldExecutive(fieldExecutive);
        } catch (SQLException e) {
            LOGGER.error("Error saving field executive account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error registering field executive.");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET method for this request.");
    }
}
