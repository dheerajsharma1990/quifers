package com.quifers.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.lang.reflect.Field;

public class FieldExecutive implements Serializable {

    private String fieldExecutiveId;

    private FieldExecutiveAccount account;

    private String name;

    private long mobileNumber;

    public FieldExecutive() {
    }

    public FieldExecutive(FieldExecutiveAccount account, String name, long mobileNumber) {
        setFieldExecutiveId(account.getUserId());
        this.account = account;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public FieldExecutiveAccount getAccount() {
        return account;
    }

    public String getName() {
        return name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setAccount(FieldExecutiveAccount account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldExecutive that = (FieldExecutive) o;

        if (mobileNumber != that.mobileNumber) return false;
        if (account != null ? !account.equals(that.account) : that.account != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return account != null ? account.hashCode() : 0;
    }

    @Override
    public String toString() {
        return (new ReflectionToStringBuilder(this, ToStringStyle.SIMPLE_STYLE) {
            protected boolean accept(Field f) {
                return super.accept(f) && !f.getName().equals("account");
            }
        }).toString();
    }

    public String getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public void setFieldExecutiveId(String fieldExecutiveId) {
        this.fieldExecutiveId = fieldExecutiveId;
    }
}
