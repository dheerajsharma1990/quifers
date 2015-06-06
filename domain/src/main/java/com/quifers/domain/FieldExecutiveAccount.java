package com.quifers.domain;

import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class FieldExecutiveAccount implements Serializable {

    private String userId;

    private String password;

    public FieldExecutiveAccount() {
    }

    public FieldExecutiveAccount(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    public String getUserId() {
        return userId;
    }

    public String getPassword() {
        return password;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldExecutiveAccount account = (FieldExecutiveAccount) o;

        if (password != null ? !password.equals(account.password) : account.password != null) return false;
        if (userId != null ? !userId.equals(account.userId) : account.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
