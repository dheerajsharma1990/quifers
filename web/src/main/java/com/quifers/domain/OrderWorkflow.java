package com.quifers.domain;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.util.Date;

@Table(name = "order_workflow")
public class OrderWorkflow implements QuifersDomainObject {

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "order_state")
    private OrderState orderState;

    @Column(name = "effective_time")
    private Date effectiveTime;

    public OrderWorkflow() {

    }

    public OrderWorkflow(long orderId, OrderState orderState, Date effectiveTime) {
        this.orderId = orderId;
        this.orderState = orderState;
        this.effectiveTime = effectiveTime;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
