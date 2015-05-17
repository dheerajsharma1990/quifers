package com.quifers.domain;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;

import static org.apache.commons.lang3.builder.ToStringBuilder.reflectionToString;

@Table(name = "field_executive")
public class FieldExecutive implements QuifersDomainObject {

    @Column(name = "user_id")
    private String userId;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_number")
    private long mobileNumber;

    public FieldExecutive() {
    }

    public FieldExecutive(String userId, String name, long mobileNumber) {
        this.userId = userId;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    @Override
    public String toString() {
        return reflectionToString(this);
    }
}
