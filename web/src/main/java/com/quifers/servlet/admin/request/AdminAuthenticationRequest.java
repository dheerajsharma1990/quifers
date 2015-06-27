package com.quifers.servlet.admin.request;

import com.quifers.domain.id.AdminId;
import com.quifers.servlet.ApiRequest;

public class AdminAuthenticationRequest implements ApiRequest {

    private final AdminId adminId;
    private final String accessToken;

    public AdminAuthenticationRequest(AdminId adminId, String accessToken) {
        this.adminId = adminId;
        this.accessToken = accessToken;
    }

    public AdminId getAdminId() {
        return adminId;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
