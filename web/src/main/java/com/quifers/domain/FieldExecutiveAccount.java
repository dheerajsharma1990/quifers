package com.quifers.domain;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@Table(name = "field_executive_account")
public class FieldExecutiveAccount implements QuifersDomainObject {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
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
