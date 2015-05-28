package com.quifers.servlet.admin;

import com.quifers.dao.FieldExecutiveRegisterDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.request.validators.InvalidRequestException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.quifers.listener.StartupContextListener.FIELD_EXECUTIVE_REGISTER_DAO;
import static com.quifers.request.validators.FieldExecutiveAccountValidator.*;

public class FieldExecutiveRegisterServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveRegisterServlet.class);
    private FieldExecutiveRegisterDao fieldExecutiveRegisterDao;

    @Override
    public void init() throws ServletException {
        fieldExecutiveRegisterDao = (FieldExecutiveRegisterDao) getServletContext().getAttribute(FIELD_EXECUTIVE_REGISTER_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String userId = request.getParameter("userId");
            String password = request.getParameter("password");
            String name = request.getParameter("name");
            String mobile = request.getParameter("mobileNumber");
            validateUserId(userId);
            validatePassword(password);
            validateName(name);
            validateMobileNumber(mobile);
            FieldExecutiveAccount executiveAccount = new FieldExecutiveAccount(userId, password);
            FieldExecutive fieldExecutive = new FieldExecutive(userId, name, Long.valueOf(mobile));
            fieldExecutiveRegisterDao.saveFieldExecutive(executiveAccount, fieldExecutive);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (SQLException e) {
            LOGGER.error("Error occurred in registering field executive account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
