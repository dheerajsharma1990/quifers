package com.quifers.domain;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class Admin {

    private String userId;

    private AdminAccount account;

    private String name;

    private long mobileNumber;

    public Admin() {
    }

    public Admin(AdminAccount account, String name, long mobileNumber) {
        this.userId = account.getUserId();
        this.account = account;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public String getUserId() {
        return userId;
    }

    public void setAccount(AdminAccount account) {
        this.account = account;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setUserId(String userId) {
        this.userId = userId;

    }

    public AdminAccount getAccount() {
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

        Admin admin = (Admin) o;

        if (mobileNumber != admin.mobileNumber) return false;
        if (account != null ? !account.equals(admin.account) : admin.account != null) return false;
        if (name != null ? !name.equals(admin.name) : admin.name != null) return false;

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
