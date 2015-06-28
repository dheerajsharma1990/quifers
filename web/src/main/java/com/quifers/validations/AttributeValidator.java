package com.quifers.validations;

public interface AttributeValidator<T> {

    T validate(String attributeValue) throws InvalidRequestException;

}
