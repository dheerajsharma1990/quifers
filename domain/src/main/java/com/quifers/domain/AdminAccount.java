package com.quifers.domain;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class AdminAccount {

    private String userId;

    private String password;

    public AdminAccount(String userId, String password) {
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

        AdminAccount account = (AdminAccount) o;

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
        return reflectionToString(this);
    }
}
