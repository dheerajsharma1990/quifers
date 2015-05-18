package com.quifers.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class Order implements Serializable {

    private long orderId;

    private String name;

    private long mobileNumber;

    private String email;

    private String fromAddress;

    private String toAddress;

    private String fieldExecutiveId;

    private Collection<OrderWorkflow> orderWorkflows;

    public Order() {
    }

    public Order(long orderId, String name, long mobileNumber, String email, String fromAddress, String toAddress, String fieldExecutiveId, Collection<OrderWorkflow> orderWorkflows) {
        this.orderId = orderId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.fieldExecutiveId = fieldExecutiveId;
        this.orderWorkflows = orderWorkflows;
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

    public Collection<OrderWorkflow> getOrderWorkflows() {
        return orderWorkflows;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFromAddress(String fromAddress) {
        this.fromAddress = fromAddress;
    }

    public void setToAddress(String toAddress) {
        this.toAddress = toAddress;
    }

    public void setFieldExecutiveId(String fieldExecutiveId) {
        this.fieldExecutiveId = fieldExecutiveId;
    }

    public void setOrderWorkflows(Collection<OrderWorkflow> orderWorkflows) {
        this.orderWorkflows = orderWorkflows;
    }

    public void addOrderWorkflow(OrderWorkflow orderWorkflow) {
        if (orderWorkflows == null) {
            orderWorkflows = new HashSet<>();
        }
        this.orderWorkflows.add(orderWorkflow);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (mobileNumber != order.mobileNumber) return false;
        if (orderId != order.orderId) return false;
        if (!email.equals(order.email)) return false;
        if (fieldExecutiveId != null ? !fieldExecutiveId.equals(order.fieldExecutiveId) : order.fieldExecutiveId != null)
            return false;
        if (!fromAddress.equals(order.fromAddress)) return false;
        if (!name.equals(order.name)) return false;
        if (orderWorkflows != null ? !orderWorkflows.equals(order.orderWorkflows) : order.orderWorkflows != null)
            return false;
        if (!toAddress.equals(order.toAddress)) return false;

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
