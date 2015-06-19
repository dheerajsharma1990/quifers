package com.quifers.domain;

import com.quifers.domain.enums.AddressType;
import com.quifers.domain.id.AddressId;
import com.quifers.domain.id.OrderId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

public class Address implements Serializable {

    private AddressId addressId;

    private String addressHouseNumber;

    private String addressSociety;

    private String addressArea;

    private String addressCity;

    public Address() {
    }

    public Address(OrderId orderId, AddressType addressType, String addressHouseNumber, String addressSociety, String addressArea, String addressCity) {
        this.addressId = new AddressId(orderId.getOrderId(), addressType);
        this.addressHouseNumber = addressHouseNumber;
        this.addressSociety = addressSociety;
        this.addressArea = addressArea;
        this.addressCity = addressCity;
    }

    public AddressId getAddressId() {
        return addressId;
    }

    public void setAddressId(AddressId addressId) {
        this.addressId = addressId;
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

        if (addressArea != null ? !addressArea.equals(address.addressArea) : address.addressArea != null) return false;
        if (addressCity != null ? !addressCity.equals(address.addressCity) : address.addressCity != null) return false;
        if (addressHouseNumber != null ? !addressHouseNumber.equals(address.addressHouseNumber) : address.addressHouseNumber != null)
            return false;
        if (addressId != null ? !addressId.equals(address.addressId) : address.addressId != null) return false;
        if (addressSociety != null ? !addressSociety.equals(address.addressSociety) : address.addressSociety != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        return addressId != null ? addressId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this, ToStringStyle.JSON_STYLE);
    }
}
