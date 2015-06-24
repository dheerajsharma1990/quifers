package com.quifers.response;

import com.quifers.domain.Address;
import com.quifers.domain.Client;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;

import java.util.HashSet;
import java.util.Set;

public class OrderResponse {

    private String orderId;

    private Client client;

    private String vehicle;

    private Set<AddressResponse> addresses;

    private int labours;

    private int distance;

    private int pickupFloors;

    private boolean pickupLiftWorking;

    private int dropOffFloors;

    private boolean dropOffLiftWorking;

    private Set<OrderWorkflowResponse> orderWorkflows;

    public OrderResponse(Order order) {
        this.orderId = order.getOrderId().getOrderId();
        this.client = order.getClient();
        this.vehicle = order.getVehicle();
        Set<AddressResponse> addressResponses = new HashSet<>();
        for(Address address : order.getAddresses()) {
            addressResponses.add(new AddressResponse(address));
        }
        this.addresses = addressResponses;
        this.labours = order.getLabours();
        this.distance = order.getDistance();
        this.pickupFloors = order.getPickupFloors();
        this.pickupLiftWorking = order.isPickupLiftWorking();
        this.dropOffFloors = order.getDropOffFloors();
        this.dropOffLiftWorking = order.isDropOffLiftWorking();
        Set<OrderWorkflowResponse> orderWorkflowResponses = new HashSet<>();
        for(OrderWorkflow orderWorkflow : order.getOrderWorkflows()) {
            orderWorkflowResponses.add(new OrderWorkflowResponse(orderWorkflow));
        }
        this.orderWorkflows = orderWorkflowResponses;

    }

    public String getOrderId() {
        return orderId;
    }

    public Client getClient() {
        return client;
    }

    public String getVehicle() {
        return vehicle;
    }

    public Set<AddressResponse> getAddresses() {
        return addresses;
    }

    public int getLabours() {
        return labours;
    }

    public int getDistance() {
        return distance;
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

    public Set<OrderWorkflowResponse> getOrderWorkflows() {
        return orderWorkflows;
    }
}
