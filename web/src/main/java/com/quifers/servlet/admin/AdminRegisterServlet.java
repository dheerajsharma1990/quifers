package com.quifers.servlet.admin;

import com.quifers.dao.AdminDao;
import com.quifers.domain.Admin;
import com.quifers.request.validators.AdminRegisterRequestValidator;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.quifers.servlet.listener.StartupContextListener.ADMIN_DAO;
import static com.quifers.servlet.listener.StartupContextListener.ADMIN_REQUEST_VALIDATOR;

public class AdminRegisterServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRegisterServlet.class);
    private AdminRegisterRequestValidator adminRegisterRequestValidator;
    private AdminDao adminDao;

    @Override
    public void init() throws ServletException {
        adminRegisterRequestValidator = (AdminRegisterRequestValidator) getServletContext().getAttribute(ADMIN_REQUEST_VALIDATOR);
        adminDao = (AdminDao) getServletContext().getAttribute(ADMIN_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Admin admin = adminRegisterRequestValidator.validateAdminAccountRequest(request);
            adminDao.saveAdmin(admin);
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("Error occurred in registering admin account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
