package com.quifers.domain;

import com.quifers.domain.id.FieldExecutiveId;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

public class FieldExecutiveAccount implements Serializable {

    private FieldExecutiveId fieldExecutiveId;

    private String password;

    public FieldExecutiveAccount() {
    }

    public FieldExecutiveAccount(FieldExecutiveId fieldExecutiveId, String password) {
        this.fieldExecutiveId = fieldExecutiveId;
        this.password = password;
    }

    public FieldExecutiveAccount(String fieldExecutiveId, String password) {
        this.fieldExecutiveId = new FieldExecutiveId(fieldExecutiveId);
        this.password = password;
    }

    public FieldExecutiveId getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public String getPassword() {
        return password;
    }

    public void setFieldExecutiveId(FieldExecutiveId fieldExecutiveId) {
        this.fieldExecutiveId = fieldExecutiveId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessToken() throws NoSuchAlgorithmException {
        String combined = fieldExecutiveId.getUserId() + password;
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

        FieldExecutiveAccount that = (FieldExecutiveAccount) o;

        if (fieldExecutiveId != null ? !fieldExecutiveId.equals(that.fieldExecutiveId) : that.fieldExecutiveId != null)
            return false;
        if (password != null ? !password.equals(that.password) : that.password != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fieldExecutiveId != null ? fieldExecutiveId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
