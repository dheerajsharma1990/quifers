package com.quifers.domain;

import com.quifers.domain.id.AdminId;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class AdminAccount {

    private AdminId adminId;

    private String password;

    public AdminAccount() {
    }

    public AdminAccount(AdminId adminId, String password) {
        this.adminId = adminId;
        this.password = password;
    }

    public AdminId getAdminId() {
        return adminId;
    }

    public void setAdminId(AdminId adminId) {
        this.adminId = adminId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() throws NoSuchAlgorithmException {
        String combined = adminId.getUserId() + password;
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(combined.getBytes());
        byte[] bytes = md.digest();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdminAccount that = (AdminAccount) o;

        if (adminId != null ? !adminId.equals(that.adminId) : that.adminId != null) return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

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
