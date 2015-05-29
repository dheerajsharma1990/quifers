package com.quifers.servlet.executives;

import com.quifers.authentication.AccessTokenGenerator;
import com.quifers.authentication.AdminAuthenticationData;
import com.quifers.authentication.FieldExecutiveAuthenticator;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.request.LoginRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.response.FieldExecutiveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.request.transformers.FieldExecutiveTransformer.transform;
import static com.quifers.response.AdminLoginResponse.getSuccessResponse;
import static com.quifers.servlet.listener.StartupContextListener.ADMIN_TOKEN_GENERATOR;
import static com.quifers.servlet.listener.StartupContextListener.FIELD_EXECUTIVE_AUTHENTICATOR;

public class FieldExecutiveLoginServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveLoginServlet.class);

    private FieldExecutiveAuthenticator fieldExecutiveAuthenticator;
    private AccessTokenGenerator tokenGenerator;

    @Override
    public void init() throws ServletException {
        fieldExecutiveAuthenticator = (FieldExecutiveAuthenticator) getServletContext().getAttribute(FIELD_EXECUTIVE_AUTHENTICATOR);
        tokenGenerator = (AccessTokenGenerator) getServletContext().getAttribute(ADMIN_TOKEN_GENERATOR);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            LoginRequest loginRequest = new LoginRequest(request);
            FieldExecutiveAccount account = transform(loginRequest);
            String loginResponse;
            if (!fieldExecutiveAuthenticator.isValidFieldExecutive(account)) {
                loginResponse = FieldExecutiveResponse.getInvalidLoginResponse();
            } else {
                String accessToken = tokenGenerator.generateAccessToken(account);
                AdminAuthenticationData.putFieldExecutiveToken(account.getUserId(), accessToken);
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
