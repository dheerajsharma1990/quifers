package com.quifers.request.guest;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.servlet.ApiRequest;

public class AdminRegisterRequest implements ApiRequest {

    private final AdminAccount adminAccount;
    private final Admin admin;

    public AdminRegisterRequest(AdminAccount adminAccount, Admin admin) {
        this.adminAccount = adminAccount;
        this.admin = admin;
    }

    public AdminAccount getAdminAccount() {
        return adminAccount;
    }

    public Admin getAdmin() {
        return admin;
    }
}
