package com.quifers.servlet.admin;

import com.quifers.authentication.AdminAccessTokenGenerator;
import com.quifers.authentication.AdminAuthenticationData;
import com.quifers.authentication.AdminAuthenticator;
import com.quifers.domain.AdminAccount;
import com.quifers.request.validators.AdminAccountRegisterRequestValidator;
import com.quifers.request.validators.InvalidRequestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

import static com.quifers.listener.StartupContextListener.ADMIN_ACCOUNT_REQUEST_VALIDATOR;
import static com.quifers.listener.StartupContextListener.ADMIN_AUTHENTICATOR;
import static com.quifers.listener.StartupContextListener.ADMIN_TOKEN_GENERATOR;

public class AdminLoginServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminLoginServlet.class);

    private AdminAccountRegisterRequestValidator adminAccountRegisterRequestValidator;
    private AdminAuthenticator adminAuthenticator;
    private AdminAccessTokenGenerator tokenGenerator;

    @Override
    public void init() throws ServletException {
        adminAccountRegisterRequestValidator = (AdminAccountRegisterRequestValidator) getServletContext().getAttribute(ADMIN_ACCOUNT_REQUEST_VALIDATOR);
        adminAuthenticator = (AdminAuthenticator) getServletContext().getAttribute(ADMIN_AUTHENTICATOR);
        tokenGenerator = (AdminAccessTokenGenerator) getServletContext().getAttribute(ADMIN_TOKEN_GENERATOR);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            AdminAccount account = adminAccountRegisterRequestValidator.validateAdminAccountRequest(request);
            if (!adminAuthenticator.isValidAdmin(account)) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials.");
                return;
            } else {
                String accessToken = tokenGenerator.generateAccessToken(account);
                AdminAuthenticationData.putAccessToken(account.getUserId(), accessToken);
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("access_token", accessToken);
                response.setContentType("application/json");
                response.getWriter().write(jsonObject.toString());
            }
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("Error occurred in validating admin account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            LOGGER.error("Error occurred in generating access token.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
