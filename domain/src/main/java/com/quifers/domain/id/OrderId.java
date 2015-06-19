package com.quifers.domain.id;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class OrderId implements Serializable {

    private String orderId;

    public OrderId() {
    }

    public OrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderId id = (OrderId) o;

        if (orderId != null ? !orderId.equals(id.orderId) : id.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
