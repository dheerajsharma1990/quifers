package com.quifers.request.transformers;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.request.AdminLoginRequest;
import com.quifers.request.AdminRegisterRequest;

import static java.lang.Long.valueOf;

public class AdminTransformer {

    public static Admin transform(AdminRegisterRequest adminRegisterRequest) {
        AdminAccount adminAccount = new AdminAccount(adminRegisterRequest.getUserId(), adminRegisterRequest.getPassword());
        return new Admin(adminAccount, adminRegisterRequest.getName(), valueOf(adminRegisterRequest.getMobileNumber()));
    }

    public static AdminAccount transform(AdminLoginRequest adminLoginRequest) {
        return new AdminAccount(adminLoginRequest.getUserId(), adminLoginRequest.getPassword());
    }

}
