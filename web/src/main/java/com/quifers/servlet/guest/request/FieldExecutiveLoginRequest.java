package com.quifers.servlet.guest.request;

import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.servlet.ApiRequest;

public class FieldExecutiveLoginRequest implements ApiRequest {

    private final FieldExecutiveAccount fieldExecutiveAccount;

    public FieldExecutiveLoginRequest(FieldExecutiveAccount fieldExecutiveAccount) {
        this.fieldExecutiveAccount = fieldExecutiveAccount;
    }

    public FieldExecutiveAccount getFieldExecutiveAccount() {
        return fieldExecutiveAccount;
    }
}
