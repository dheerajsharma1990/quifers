package com.quifers.domain;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@Table(name = "field_manager_account")
public class FieldManagerAccount implements QuifersDomainObject {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "password")
    private String password;

    public FieldManagerAccount() {
    }

    public FieldManagerAccount(String userId, String password) {
        this.userId = userId;
        this.password = password;
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
