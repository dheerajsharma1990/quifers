package com.quifers.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class Order implements Serializable {

    private long orderId;

    private String name;

    private long mobileNumber;

    private String email;

    private String fromAddress;

    private String toAddress;

    private String fieldExecutiveId;

    public Order(long orderId, String name, long mobileNumber, String email, String fromAddress, String toAddress, String fieldExecutiveId) {
        this.orderId = orderId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.fieldExecutiveId = fieldExecutiveId;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public String getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (orderId != order.orderId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (orderId ^ (orderId >>> 32));
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
