package com.quifers.request.transformers;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.request.FieldExecutiveRegisterRequest;

import static java.lang.Long.valueOf;

public class FieldExecutiveTransformer {

    public static FieldExecutive transform(FieldExecutiveRegisterRequest registerRequest) {
        FieldExecutiveAccount fieldExecutiveAccount = new FieldExecutiveAccount(registerRequest.getFieldExecutiveId(), registerRequest.getPassword());
        return new FieldExecutive(fieldExecutiveAccount, registerRequest.getName(), valueOf(registerRequest.getMobileNumber()));
    }
}
