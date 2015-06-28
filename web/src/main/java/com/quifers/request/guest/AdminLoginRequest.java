package com.quifers.request.guest;

import com.quifers.domain.AdminAccount;
import com.quifers.servlet.ApiRequest;

public class AdminLoginRequest implements ApiRequest {

    private final AdminAccount adminAccount;

    public AdminLoginRequest(AdminAccount adminAccount) {
        this.adminAccount = adminAccount;
    }

    public AdminAccount getAdminAccount() {
        return adminAccount;
    }
}
