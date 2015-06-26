package com.quifers.servlet.guest;

import com.quifers.authentication.AdminAuthenticationData;
import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.Order;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.AdminId;
import com.quifers.request.AdminRegisterRequest;
import com.quifers.request.LoginRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.response.AdminLoginResponse;
import com.quifers.response.FieldExecutiveResponse;
import com.quifers.servlet.BaseServlet;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.request.transformers.AdminTransformer.transform;
import static com.quifers.request.validators.admin.AdminRegisterRequestValidator.validateAdminRegisterRequest;
import static com.quifers.response.AdminRegisterResponse.getSuccessResponse;

public class GuestServlet extends BaseServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestUri = request.getRequestURI();
            if ("/api/v0/guest/admin/register".equals(requestUri)) {
                AdminRegisterRequest registerRequest = new AdminRegisterRequest(request);
                validateAdminRegisterRequest(registerRequest);
                Admin admin = transform(registerRequest);
                adminAccountDao.saveAdminAccount(new AdminAccount(new AdminId(registerRequest.getUserId()), registerRequest.getPassword()));
                adminDao.saveAdmin(admin);
                String successResponse = getSuccessResponse();
                response.setContentType("application/json");
                response.getWriter().write(successResponse);
            } else if ("/api/v0/guest/order/book".equals(requestUri)) {
                Order order = orderBookRequestValidator.validateRequest(request);
                orderDao.saveOrder(order);
                webPublisher.publishEmailMessage(EmailType.NEW_ORDER, order.getOrderId());
                JSONObject object = new JSONObject();
                object.put("success", "true");
                object.put("order_state", OrderState.BOOKED.name());
                object.put("order_id", order.getOrderId().getOrderId());
                response.setContentType("application/json");
                response.getWriter().write(object.toString());
            } else if ("/api/v0/guest/admin/login".equals(requestUri)) {
                LoginRequest loginRequest = new LoginRequest(request);
                AdminAccount adminAccount = transform(loginRequest);
                response.setContentType("application/json");
                String loginResponse;
                if (!adminAuthenticator.isValidAdmin(adminAccount)) {
                    loginResponse = AdminLoginResponse.getInvalidLoginResponse();
                } else {
                    String accessToken = adminAccount.getAccessToken();
                    AdminAuthenticationData.putAdminAccessToken(adminAccount.getAdminId().getUserId(), accessToken);
                    loginResponse = AdminLoginResponse.getSuccessResponse(accessToken);
                }
                response.getWriter().write(loginResponse);
            } else if ("/api/v0/guest/executive/login".equals(requestUri)) {
                LoginRequest loginRequest = new LoginRequest(request);
                FieldExecutiveAccount account = new FieldExecutiveAccount(loginRequest.getUserId(), loginRequest.getPassword());
                String loginResponse;
                if (!fieldExecutiveAuthenticator.isValidFieldExecutive(account)) {
                    loginResponse = FieldExecutiveResponse.getInvalidLoginResponse();
                } else {
                    String accessToken = account.getAccessToken();
                    AdminAuthenticationData.putFieldExecutiveToken(account.getFieldExecutiveId().getUserId(), accessToken);
                    loginResponse = AdminLoginResponse.getSuccessResponse(accessToken);
                }
                response.getWriter().write(loginResponse);
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Some error occurred.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
