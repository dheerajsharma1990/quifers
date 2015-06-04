package com.quifers.domain;

import com.quifers.domain.enums.AddressType;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

public class Address implements Serializable {

    private long orderId;

    private AddressType addressType;

    private String addressHouseNumber;

    private String addressSociety;

    private String addressArea;

    private String addressCity;

    public Address() {
    }

    public Address(long orderId, AddressType addressType, String addressHouseNumber, String addressSociety, String addressArea, String addressCity) {
        this.orderId = orderId;
        this.addressType = addressType;
        this.addressHouseNumber = addressHouseNumber;
        this.addressSociety = addressSociety;
        this.addressArea = addressArea;
        this.addressCity = addressCity;
    }

    public long getOrderId() {
        return orderId;
    }

    public AddressType getAddressType() {
        return addressType;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setAddressType(AddressType addressType) {
        this.addressType = addressType;
    }

    public String getAddressHouseNumber() {
        return addressHouseNumber;
    }

    public void setAddressHouseNumber(String addressHouseNumber) {
        this.addressHouseNumber = addressHouseNumber;
    }

    public String getAddressSociety() {
        return addressSociety;
    }

    public void setAddressSociety(String addressSociety) {
        this.addressSociety = addressSociety;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public void setAddressArea(String addressArea) {
        this.addressArea = addressArea;
    }

    public String getAddressCity() {
        return addressCity;
    }

    public void setAddressCity(String addressCity) {
        this.addressCity = addressCity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        if (orderId != address.orderId) return false;
        if (addressArea != null ? !addressArea.equals(address.addressArea) : address.addressArea != null) return false;
        if (addressCity != null ? !addressCity.equals(address.addressCity) : address.addressCity != null) return false;
        if (addressHouseNumber != null ? !addressHouseNumber.equals(address.addressHouseNumber) : address.addressHouseNumber != null)
            return false;
        if (addressSociety != null ? !addressSociety.equals(address.addressSociety) : address.addressSociety != null)
            return false;
        if (addressType != address.addressType) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (orderId ^ (orderId >>> 32));
        result = 31 * result + (addressType != null ? addressType.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}
