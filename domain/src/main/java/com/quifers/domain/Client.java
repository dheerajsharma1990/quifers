package com.quifers.domain;

import com.quifers.domain.id.OrderId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class Client implements Serializable {

    private OrderId orderId;

    private String name;

    private long mobileNumber;

    private String email;

    public Client() {
    }

    public Client(OrderId orderId, String name, long mobileNumber, String email) {
        this.orderId = orderId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Client client = (Client) o;

        if (mobileNumber != client.mobileNumber) return false;
        if (email != null ? !email.equals(client.email) : client.email != null) return false;
        if (name != null ? !name.equals(client.name) : client.name != null) return false;
        if (orderId != null ? !orderId.equals(client.orderId) : client.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
