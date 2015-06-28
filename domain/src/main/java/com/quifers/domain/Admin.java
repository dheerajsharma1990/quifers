package com.quifers.domain;

import com.quifers.domain.id.AdminId;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class Admin {

    private AdminId adminId;

    private String name;

    private long mobileNumber;

    public Admin() {
    }

    public Admin(AdminId adminId, String name, long mobileNumber) {
        this.adminId = adminId;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public Admin(String adminId, String name, long mobileNumber) {
        this(new AdminId(adminId), name, mobileNumber);
    }

    public AdminId getAdminId() {
        return adminId;
    }

    public void setAdminId(AdminId adminId) {
        this.adminId = adminId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Admin admin = (Admin) o;

        if (mobileNumber != admin.mobileNumber) return false;
        if (adminId != null ? !adminId.equals(admin.adminId) : admin.adminId != null) return false;
        if (name != null ? !name.equals(admin.name) : admin.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return adminId != null ? adminId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
