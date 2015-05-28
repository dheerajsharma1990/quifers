package com.quifers.servlet;

import com.quifers.authentication.AdminAuthenticator;
import com.quifers.dao.AdminRegisterDao;
import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.request.validators.AdminAccountRegisterRequestValidator;
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

import static com.quifers.listener.StartupContextListener.*;

public class AdminServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServlet.class);

    private AdminAccountRegisterRequestValidator adminAccountRegisterRequestValidator;
    private AdminAuthenticator adminAuthenticator;

    @Override
    public void init() throws ServletException {
        adminAccountRegisterRequestValidator = (AdminAccountRegisterRequestValidator) getServletContext().getAttribute(ADMIN_ACCOUNT_REQUEST_VALIDATOR);
        adminAuthenticator = (AdminAuthenticator) getServletContext().getAttribute(ADMIN_AUTHENTICATOR);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            AdminAccount account = adminAccountRegisterRequestValidator.validateAdminAccountRequest(request);
            if (!adminAuthenticator.isValidAdmin(account)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials.");
                return;
            }
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("Error occurred in validating admin account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
