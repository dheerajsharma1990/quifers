package com.quifers.domain.id;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class FieldExecutiveId implements Serializable {

    private String userId;

    public FieldExecutiveId() {
    }

    public FieldExecutiveId(String userId) {
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        FieldExecutiveId fieldExecutiveId = (FieldExecutiveId) o;

        if (userId != null ? !userId.equals(fieldExecutiveId.userId) : fieldExecutiveId.userId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
