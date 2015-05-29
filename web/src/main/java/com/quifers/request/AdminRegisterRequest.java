package com.quifers.request;

import javax.servlet.http.HttpServletRequest;

public class AdminRegisterRequest {

    private String userId;
    private String password;
    private String name;
    private String mobileNumber;

    public AdminRegisterRequest(HttpServletRequest request) {
        this.userId = request.getParameter("user_id");
        this.password = request.getParameter("password");
        this.name = request.getParameter("name");
        this.mobileNumber = request.getParameter("mobile_number");
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }
}
