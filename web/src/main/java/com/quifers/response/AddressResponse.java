package com.quifers.response;

import com.quifers.domain.Address;

public class AddressResponse {

    private String addressType;

    private String addressHouseNumber;

    private String addressSociety;

    private String addressArea;

    private String addressCity;

    public AddressResponse(Address address) {
        this.addressType = address.getAddressId().getAddressType().name();
        this.addressHouseNumber = address.getAddressHouseNumber();
        this.addressSociety = address.getAddressSociety();
        this.addressArea = address.getAddressArea();
        this.addressCity = address.getAddressCity();
    }


    public String getAddressType() {
        return addressType;
    }

    public String getAddressHouseNumber() {
        return addressHouseNumber;
    }

    public String getAddressSociety() {
        return addressSociety;
    }

    public String getAddressArea() {
        return addressArea;
    }

    public String getAddressCity() {
        return addressCity;
    }
}
