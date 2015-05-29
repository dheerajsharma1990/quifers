package com.quifers.servlet.admin;

import com.quifers.authentication.AccessTokenGenerator;
import com.quifers.authentication.AdminAuthenticationData;
import com.quifers.authentication.AdminAuthenticator;
import com.quifers.domain.AdminAccount;
import com.quifers.request.AdminLoginRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.response.AdminLoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.request.transformers.AdminTransformer.transform;
import static com.quifers.request.validators.admin.AdminLoginRequestValidator.validateAdminLoginRequest;
import static com.quifers.response.AdminLoginResponse.getSuccessResponse;
import static com.quifers.servlet.listener.StartupContextListener.ADMIN_AUTHENTICATOR;
import static com.quifers.servlet.listener.StartupContextListener.ADMIN_TOKEN_GENERATOR;

public class AdminLoginServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminLoginServlet.class);

    private AdminAuthenticator adminAuthenticator;
    private AccessTokenGenerator tokenGenerator;

    @Override
    public void init() throws ServletException {
        adminAuthenticator = (AdminAuthenticator) getServletContext().getAttribute(ADMIN_AUTHENTICATOR);
        tokenGenerator = (AccessTokenGenerator) getServletContext().getAttribute(ADMIN_TOKEN_GENERATOR);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            AdminLoginRequest adminLoginRequest = new AdminLoginRequest(request);
            validateAdminLoginRequest(adminLoginRequest);
            AdminAccount adminAccount = transform(adminLoginRequest);
            response.setContentType("application/json");
            String loginResponse;
            if (!adminAuthenticator.isValidAdmin(adminAccount)) {
                loginResponse = AdminLoginResponse.getInvalidLoginResponse();
            } else {
                String accessToken = tokenGenerator.generateAccessToken(adminAccount);
                AdminAuthenticationData.putAdminAccessToken(adminAccount.getUserId(), accessToken);
                loginResponse = getSuccessResponse(accessToken);
            }
            response.getWriter().write(loginResponse);
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Throwable e) {
            LOGGER.error("Error occurred in validating admin account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}