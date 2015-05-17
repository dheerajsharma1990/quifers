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

    public FieldExecutiveAccount() {
    }

    public FieldExecutiveAccount(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
