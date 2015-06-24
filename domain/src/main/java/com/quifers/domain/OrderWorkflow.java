package com.quifers.domain;

import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import com.quifers.domain.id.OrderWorkflowId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Date;

public class OrderWorkflow implements Serializable {

    private OrderWorkflowId orderWorkflowId;

    private Date effectiveTime;

    private boolean currentState = true;

    public OrderWorkflow() {
    }

    public OrderWorkflow(OrderId orderId, OrderState orderState, Date effectiveTime, boolean currentState) {
        this.orderWorkflowId = new OrderWorkflowId(orderId.getOrderId(), orderState);
        this.effectiveTime = effectiveTime;
        this.currentState = currentState;
    }


    public OrderWorkflowId getOrderWorkflowId() {
        return orderWorkflowId;
    }

    public void setOrderWorkflowId(OrderWorkflowId orderWorkflowId) {
        this.orderWorkflowId = orderWorkflowId;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public boolean isCurrentState() {
        return currentState;
    }

    public void setCurrentState(boolean currentState) {
        this.currentState = currentState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderWorkflow that = (OrderWorkflow) o;

        if (currentState != that.currentState) return false;
        if (effectiveTime != null ? !effectiveTime.equals(that.effectiveTime) : that.effectiveTime != null)
            return false;
        if (orderWorkflowId != null ? !orderWorkflowId.equals(that.orderWorkflowId) : that.orderWorkflowId != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return orderWorkflowId != null ? orderWorkflowId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
