package com.quifers.response;

public class FieldExecutiveListAll {
    private String userId;
    private String name;
    private Long mobileNumber;

    public FieldExecutiveListAll() {

    }

    public FieldExecutiveListAll(String userId, String name, Long mobileNumber) {
        this.userId = userId;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public Long getMobileNumber() {
        return mobileNumber;
    }

}
