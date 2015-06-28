package com.quifers.request.executive;

import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.id.OrderId;
import com.quifers.servlet.ApiRequest;

public class CreatePriceRequest implements ApiRequest {

    private final OrderId orderId;
    private final int distance;
    private final int waitingMinutes;
    private final int pickupFloors;
    private final boolean pickupLiftWorking;
    private final int dropOffFloors;
    private final boolean dropOffLiftWorking;
    private final OrderWorkflow orderWorkflow;

    public CreatePriceRequest(OrderId orderId, int distance, int waitingMinutes, int pickupFloors,
                              boolean pickupLiftWorking, int dropOffFloors, boolean dropOffLiftWorking, OrderWorkflow orderWorkflow) {
        this.orderId = orderId;
        this.distance = distance;
        this.waitingMinutes = waitingMinutes;
        this.pickupFloors = pickupFloors;
        this.pickupLiftWorking = pickupLiftWorking;
        this.dropOffFloors = dropOffFloors;
        this.dropOffLiftWorking = dropOffLiftWorking;
        this.orderWorkflow = orderWorkflow;
    }

    public OrderId getOrderId() {
        return orderId;
    }

    public int getDistance() {
        return distance;
    }

    public int getWaitingMinutes() {
        return waitingMinutes;
    }

    public int getPickupFloors() {
        return pickupFloors;
    }

    public boolean isPickupLiftWorking() {
        return pickupLiftWorking;
    }

    public int getDropOffFloors() {
        return dropOffFloors;
    }

    public boolean isDropOffLiftWorking() {
        return dropOffLiftWorking;
    }

    public OrderWorkflow getOrderWorkflow() {
        return orderWorkflow;
    }
}
