package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class FieldExecutiveRegisterRequest {

    private final String fieldExecutiveId;
    private final String password;
    private final String name;
    private final String mobileNumber;

    public FieldExecutiveRegisterRequest(HttpServletRequest request) throws InvalidRequestException {
        fieldExecutiveId = request.getParameter("field_executive_id");
        password = request.getParameter("password");
        name = request.getParameter("name");
        mobileNumber = request.getParameter("mobile_number");
        validate();
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

    public String getMobileNumber() {
        return mobileNumber;
    }

    private void validate() throws InvalidRequestException {
        if (isEmpty(fieldExecutiveId)) {
            throw new InvalidRequestException("Field Executive Id cannot be empty.");
        }
        if (isEmpty(password)) {
            throw new InvalidRequestException("Password cannot be empty.");
        }
        if (isEmpty(name)) {
            throw new InvalidRequestException("Name cannot be empty.");
        }
        if (isEmpty(mobileNumber)) {
            throw new InvalidRequestException("Mobile Number cannot be empty.");
        }

    }


}
