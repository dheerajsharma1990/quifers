package com.quifers.domain;

import com.quifers.domain.enums.OrderState;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class OrderWorkflow implements Serializable {

    private String orderId;

    private OrderState orderState;

    private Date effectiveTime;

    public OrderWorkflow() {
    }

    public OrderWorkflow(String orderId, OrderState orderState, Date effectiveTime) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.effectiveTime = effectiveTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderWorkflow workflow = (OrderWorkflow) o;

        if (effectiveTime != null ? !effectiveTime.equals(workflow.effectiveTime) : workflow.effectiveTime != null)
            return false;
        if (orderId != null ? !orderId.equals(workflow.orderId) : workflow.orderId != null) return false;
        if (orderState != workflow.orderState) return false;

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
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }
}
