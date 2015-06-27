package com.quifers.service;

import java.util.concurrent.atomic.AtomicLong;

public class OrderIdGeneratorService {

    private static AtomicLong counter = new AtomicLong();

    public static final String ORDER_ID_PREFIX = "QUIFID";
    public static final int PADDING = 8;

    public OrderIdGeneratorService(long lastOrderIdValue) {
        counter.set(lastOrderIdValue);
    }

    public String getNewOrderId() {
        return ORDER_ID_PREFIX + String.format("%0" + PADDING + "d", counter.incrementAndGet());
    }

    public static int getOrderIdLength() {
        return ORDER_ID_PREFIX.length() + PADDING;
    }

}
