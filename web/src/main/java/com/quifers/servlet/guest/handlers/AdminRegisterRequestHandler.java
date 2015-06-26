package com.quifers.servlet.guest.handlers;


import com.quifers.dao.AdminAccountDao;
import com.quifers.dao.AdminDao;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.guest.request.AdminRegisterRequest;
import com.quifers.servlet.guest.validators.AdminRegisterRequestValidator;
import org.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AdminRegisterRequestHandler implements RequestHandler {

    private final AdminRegisterRequestValidator requestValidator;
    private final AdminAccountDao adminAccountDao;
    private final AdminDao adminDao;

    public AdminRegisterRequestHandler(AdminRegisterRequestValidator requestValidator, AdminAccountDao adminAccountDao, AdminDao adminDao) {
        this.requestValidator = requestValidator;
        this.adminAccountDao = adminAccountDao;
        this.adminDao = adminDao;
    }

    @Override
    public void handleRequest(HttpServletRequest servletRequest, HttpServletResponse servletResponse) throws Exception {
        AdminRegisterRequest registerRequest = requestValidator.validateRequest(servletRequest);
        adminAccountDao.saveAdminAccount(registerRequest.getAdminAccount());
        adminDao.saveAdmin(registerRequest.getAdmin());
        JSONObject object = new JSONObject();
        object.put("success", "true");
        servletResponse.setContentType("application/json");
        servletResponse.getWriter().write(object.toString());

    }
}
