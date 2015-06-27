package com.quifers.servlet.validations;

import com.quifers.domain.id.OrderId;

import static com.quifers.service.OrderIdGeneratorService.*;
import static org.apache.commons.lang3.StringUtils.isEmpty;

public class OrderIdAttributeValidator implements AttributeValidator<OrderId> {

    @Override
    public OrderId validate(String orderId) throws InvalidRequestException {
        if (isEmpty(orderId)) {
            throw new InvalidRequestException("Order Id is empty.");
        }
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
