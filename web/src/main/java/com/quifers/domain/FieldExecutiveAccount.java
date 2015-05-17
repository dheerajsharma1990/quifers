package com.quifers.domain;

import java.io.Serializable;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class FieldExecutiveAccount implements Serializable {

    private String userId;

    private String password;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldExecutiveAccount that = (FieldExecutiveAccount) o;

        if (!userId.equals(that.userId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
