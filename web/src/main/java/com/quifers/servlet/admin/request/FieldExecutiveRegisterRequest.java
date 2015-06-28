package com.quifers.servlet.admin.request;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.servlet.ApiRequest;

public class FieldExecutiveRegisterRequest implements ApiRequest {

    private final FieldExecutiveAccount fieldExecutiveAccount;
    private final FieldExecutive fieldExecutive;

    public FieldExecutiveRegisterRequest(FieldExecutiveAccount fieldExecutiveAccount, FieldExecutive fieldExecutive) {
        this.fieldExecutiveAccount = fieldExecutiveAccount;
        this.fieldExecutive = fieldExecutive;
    }

    public FieldExecutiveAccount getFieldExecutiveAccount() {
        return fieldExecutiveAccount;
    }

    public FieldExecutive getFieldExecutive() {
        return fieldExecutive;
    }
}
