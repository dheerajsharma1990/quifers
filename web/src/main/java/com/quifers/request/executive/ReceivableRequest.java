package com.quifers.request.executive;

import com.quifers.domain.id.OrderId;
import com.quifers.servlet.ApiRequest;

public class ReceivableRequest implements ApiRequest {

    private final OrderId orderId;

    private final int receivables;

    public ReceivableRequest(OrderId orderId, int receivables) {
        this.orderId = orderId;
        this.receivables = receivables;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public int getReceivables() {
        return receivables;
    }
}
