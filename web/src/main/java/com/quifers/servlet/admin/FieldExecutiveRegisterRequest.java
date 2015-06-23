package com.quifers.servlet.admin;

import com.quifers.servlet.ApiRequest;

public class FieldExecutiveRegisterRequest implements ApiRequest {

    private String fieldExecutiveId;
    private String password;
    private String name;
    private long mobileNumber;

    public FieldExecutiveRegisterRequest(String fieldExecutiveId, String password, String name, long mobileNumber) {
        this.fieldExecutiveId = fieldExecutiveId;
        this.password = password;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public String getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }
}
