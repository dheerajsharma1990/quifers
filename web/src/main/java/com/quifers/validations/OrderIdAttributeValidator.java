package com.quifers.validations;

import com.quifers.domain.id.OrderId;

import static com.quifers.service.OrderIdGeneratorService.ORDER_ID_PREFIX;
import static com.quifers.service.OrderIdGeneratorService.getOrderIdLength;

public class OrderIdAttributeValidator implements AttributeValidator<OrderId> {

    private final EmptyStringAttributeValidator emptyStringAttributeValidator;

    public OrderIdAttributeValidator(EmptyStringAttributeValidator emptyStringAttributeValidator) {
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
    }

    @Override
    public OrderId validate(String orderId) throws InvalidRequestException {
        orderId = emptyStringAttributeValidator.validate(orderId);
        orderId = orderId.trim().toUpperCase();
        if (!orderId.startsWith(ORDER_ID_PREFIX)) {
            throw new InvalidRequestException("Order Id [" + orderId + "] must start with [" + ORDER_ID_PREFIX + "].");
        }
        int orderIdLength = getOrderIdLength();
        if (orderId.length() != orderIdLength) {
            throw new InvalidRequestException("Order Id [" + orderId + "] does not have desired length of [" + orderIdLength + "].");
        }
        return new OrderId(orderId);
    }
}
