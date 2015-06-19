package com.quifers.domain.id;

import com.quifers.domain.enums.AddressType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class AddressId implements Serializable {

    private String orderId;

    private AddressType addressType;

    public AddressId() {
    }

    public AddressId(String orderId, AddressType addressType) {
        this.orderId = orderId;
        this.addressType = addressType;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AddressId addressId = (AddressId) o;

        if (addressType != addressId.addressType) return false;
        if (orderId != null ? !orderId.equals(addressId.orderId) : addressId.orderId != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = orderId != null ? orderId.hashCode() : 0;
        result = 31 * result + (addressType != null ? addressType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
