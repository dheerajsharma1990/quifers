package com.quifers.domain;

import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

@Table(name = "orders")
public class Order implements QuifersDomainObject {

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_number")
    private long mobileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "field_manager_id")
    private String fieldManagerId;

    public Order() {
    }

    public Order(long orderId, String name, long mobileNumber, String email, String fromAddress, String toAddress) {
        this.orderId = orderId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
    }

    public void setFieldManagerId(String fieldManagerId) {
        this.fieldManagerId = fieldManagerId;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
