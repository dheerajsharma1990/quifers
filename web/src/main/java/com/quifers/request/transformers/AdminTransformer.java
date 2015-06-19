package com.quifers.request.transformers;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;
import com.quifers.request.LoginRequest;
import com.quifers.request.AdminRegisterRequest;

import static java.lang.Long.valueOf;

public class AdminTransformer {

    public static Admin transform(AdminRegisterRequest adminRegisterRequest) {
        return new Admin(new AdminId(adminRegisterRequest.getUserId()), adminRegisterRequest.getName(), valueOf(adminRegisterRequest.getMobileNumber()));
    }

    public static AdminAccount transform(LoginRequest loginRequest) {
        return new AdminAccount(new AdminId(loginRequest.getUserId()), loginRequest.getPassword());
    }

}
