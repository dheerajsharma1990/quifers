package com.quifers.servlet.admin;

import com.quifers.dao.AdminAccountDao;
import com.quifers.dao.AdminDao;
import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.request.AdminRegisterRequest;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.request.transformers.AdminTransformer.transform;
import static com.quifers.request.validators.admin.AdminRegisterRequestValidator.validateAdminRegisterRequest;
import static com.quifers.response.AdminRegisterResponse.getSuccessResponse;
import static com.quifers.servlet.listener.StartupContextListener.ADMIN_ACCOUNT_DAO;
import static com.quifers.servlet.listener.StartupContextListener.ADMIN_DAO;

public class AdminRegisterServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminRegisterServlet.class);
    private AdminAccountDao adminAccountDao;
    private AdminDao adminDao;

    @Override
    public void init() throws ServletException {
        adminAccountDao = (AdminAccountDao) getServletContext().getAttribute(ADMIN_ACCOUNT_DAO);
        adminDao = (AdminDao) getServletContext().getAttribute(ADMIN_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            AdminRegisterRequest registerRequest = new AdminRegisterRequest(request);
            validateAdminRegisterRequest(registerRequest);
            Admin admin = transform(registerRequest);
            adminAccountDao.saveAdminAccount(new AdminAccount(new AdminId(registerRequest.getUserId()), registerRequest.getPassword()));
            adminDao.saveAdmin(admin);
            String successResponse = getSuccessResponse();
            response.setContentType("application/json");
            response.getWriter().write(successResponse);
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Throwable e) {
            LOGGER.error("Error occurred in registering admin account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
