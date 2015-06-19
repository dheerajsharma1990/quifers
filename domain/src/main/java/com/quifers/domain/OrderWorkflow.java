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

    public OrderWorkflow() {
    }

    public OrderWorkflow(OrderId orderId, OrderState orderState, Date effectiveTime) {
        this.orderWorkflowId = new OrderWorkflowId(orderId.getOrderId(), orderState);
        this.effectiveTime = effectiveTime;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderWorkflow workflow = (OrderWorkflow) o;

        if (effectiveTime != null ? !effectiveTime.equals(workflow.effectiveTime) : workflow.effectiveTime != null)
            return false;
        if (orderWorkflowId != null ? !orderWorkflowId.equals(workflow.orderWorkflowId) : workflow.orderWorkflowId != null)
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
