package com.quifers.service;

import java.util.concurrent.atomic.AtomicLong;

public class OrderIdGeneratorService {

    private static AtomicLong counter = new AtomicLong();

    public static final String ORDER_ID_PREFIX = "QUIFID";

    public OrderIdGeneratorService(long lastOrderIdValue) {
        counter.set(lastOrderIdValue);
    }

    public String getNewOrderId() {
        return ORDER_ID_PREFIX + String.format("%05d", counter.incrementAndGet());

    }

}
