package com.quifers.request;

import javax.servlet.http.HttpServletRequest;

public class AdminLoginRequest {

    private String userId;
    private String password;

    public AdminLoginRequest(HttpServletRequest request) {
        this.userId = request.getParameter("user_id");
        this.password = request.getParameter("password");
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }
}
