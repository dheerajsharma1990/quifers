package com.quifers.domain;

import com.quifers.domain.id.FieldExecutiveId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class FieldExecutive implements Serializable {

    private FieldExecutiveId fieldExecutiveId;

    private String name;

    private long mobileNumber;

    public FieldExecutive() {
    }

    public FieldExecutive(FieldExecutiveId fieldExecutiveId, String name, long mobileNumber) {
        this.fieldExecutiveId = fieldExecutiveId;
        this.name = name;
        this.mobileNumber = mobileNumber;
    }

    public FieldExecutiveId getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public void setFieldExecutiveId(FieldExecutiveId fieldExecutiveId) {
        this.fieldExecutiveId = fieldExecutiveId;
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

        FieldExecutive that = (FieldExecutive) o;

        if (mobileNumber != that.mobileNumber) return false;
        if (fieldExecutiveId != null ? !fieldExecutiveId.equals(that.fieldExecutiveId) : that.fieldExecutiveId != null)
            return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return fieldExecutiveId != null ? fieldExecutiveId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return (new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE)).toString();
    }

}
