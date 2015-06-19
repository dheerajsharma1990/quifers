package com.quifers.domain;

import com.quifers.domain.id.OrderId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class Distance implements Serializable {

    private OrderId orderId;

    private int distance;

    public Distance() {
    }

    public Distance(OrderId orderId, int distance) {
        this.orderId = orderId;
        this.distance = distance;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Distance distance1 = (Distance) o;

        if (distance != distance1.distance) return false;
        if (orderId != null ? !orderId.equals(distance1.orderId) : distance1.orderId != null) return false;

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
