package com.quifers.domain;

import com.quifers.domain.enums.OrderState;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Date;

public class OrderWorkflow implements Serializable {

    private long orderId;

    private OrderState orderState;

    private Date effectiveTime;


    public OrderWorkflow(long orderId, OrderState orderState, Date effectiveTime) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.effectiveTime = effectiveTime;
    }

    public long getOrderId() {
        return orderId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderWorkflow that = (OrderWorkflow) o;

        if (orderId != that.orderId) return false;
        if (!effectiveTime.equals(that.effectiveTime)) return false;
        if (orderState != that.orderState) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + orderState.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
