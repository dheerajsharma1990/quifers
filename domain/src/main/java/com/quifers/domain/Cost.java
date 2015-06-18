package com.quifers.domain;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class Cost implements Serializable {

    private String orderId;

    private int transitCost;

    private int waitingCost;

    private int labourCost;


    public Cost() {
    }

    public Cost(String orderId, int transitCost, int waitingCost, int labourCost) {
        this.orderId = orderId;
        this.transitCost = transitCost;
        this.waitingCost = waitingCost;
        this.labourCost = labourCost;
    }

    public String getOrderId() {
        return orderId;
    }

    public int getTransitCost() {
        return transitCost;
    }

    public int getWaitingCost() {
        return waitingCost;
    }

    public int getLabourCost() {
        return labourCost;
    }

    public int getTotalCost() {
        return transitCost + waitingCost + labourCost;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public void setTransitCost(int transitCost) {
        this.transitCost = transitCost;
    }

    public void setWaitingCost(int waitingCost) {
        this.waitingCost = waitingCost;
    }

    public void setLabourCost(int labourCost) {
        this.labourCost = labourCost;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cost cost = (Cost) o;

        if (labourCost != cost.labourCost) return false;
        if (transitCost != cost.transitCost) return false;
        if (waitingCost != cost.waitingCost) return false;
        if (orderId != null ? !orderId.equals(cost.orderId) : cost.orderId != null) return false;

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
