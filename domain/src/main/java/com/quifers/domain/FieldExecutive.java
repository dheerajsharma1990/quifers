package com.quifers.domain;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class FieldExecutive implements Serializable {

    private FieldExecutiveAccount account;

    private String name;

    private long mobileNumber;

    public FieldExecutive(FieldExecutiveAccount account, String name, long mobileNumber) {
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
        return reflectionToString(this);
    }
}
