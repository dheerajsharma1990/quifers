package com.quifers.servlet.validations;

public interface AttributeValidator<T> {

    T validate(String attributeValue) throws InvalidRequestException;

}
