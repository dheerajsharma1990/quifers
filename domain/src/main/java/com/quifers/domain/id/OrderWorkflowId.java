package com.quifers.domain.id;

import com.quifers.domain.enums.OrderState;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class OrderWorkflowId implements Serializable {

    private String orderId;

    private OrderState orderState;

    public OrderWorkflowId() {
    }

    public OrderWorkflowId(String orderId, OrderState orderState) {
        this.orderId = orderId;
        this.orderState = orderState;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderWorkflowId that = (OrderWorkflowId) o;

        if (orderId != null ? !orderId.equals(that.orderId) : that.orderId != null) return false;
        if (orderState != that.orderState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (orderState != null ? orderState.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
