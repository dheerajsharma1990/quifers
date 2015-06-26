package com.quifers.domain.builders;

import com.quifers.domain.*;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.domain.id.OrderId;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderBuilder {

    private OrderId orderId;

    private Client client;

    private String vehicle;

    private Set<Address> addresses = new HashSet<>();

    private int labours;

    private String estimate;

    private int distance;

    private int pickupFloors;

    private boolean pickupLiftWorking;

    private int dropOffFloors;

    private boolean dropOffLiftWorking;

    private FieldExecutive fieldExecutive;

    private Set<OrderWorkflow> orderWorkflows = new HashSet<>();

    private int waitingMinutes;

    private int receivables;

    public OrderBuilder(String orderId) {
        this.orderId = new OrderId(orderId);
    }

    public OrderBuilder addClient(String name, long mobileNumber, String email) {
        this.client = new Client(orderId, name, mobileNumber, email);
        return this;
    }

    public OrderBuilder addVehicle(String vehicle) {
        this.vehicle = vehicle;
        return this;
    }

    public OrderBuilder addAddress(AddressType addressType, String houseNumber, String society, String area, String city) {
        addresses.add(new Address(orderId, addressType, houseNumber, society, area, city));
        return this;
    }

    public OrderBuilder addLabours(int labours) {
        this.labours = labours;
        return this;
    }

    public OrderBuilder addEstimate(String estimate) {
        this.estimate = estimate;
        return this;
    }

    public OrderBuilder addDistance(int distance) {
        this.distance = distance;
        return this;
    }

    public OrderBuilder addPickUpFloors(int pickupFloors) {
        this.pickupFloors = pickupFloors;
        return this;
    }

    public OrderBuilder addPickUpLiftWorking(boolean pickupLiftWorking) {
        this.pickupLiftWorking = pickupLiftWorking;
        return this;
    }

    public OrderBuilder addDropOffFloors(int dropOffFloors) {
        this.dropOffFloors = dropOffFloors;
        return this;
    }

    public OrderBuilder addDropOffLiftWorking(boolean dropOffLiftWorking) {
        this.dropOffLiftWorking = dropOffLiftWorking;
        return this;
    }

    public OrderBuilder addFieldExecutive(String fieldExecutiveId, String name, long mobileNumber) {
        this.fieldExecutive = new FieldExecutive(new FieldExecutiveId(fieldExecutiveId), name, mobileNumber);
        return this;
    }

    public OrderBuilder addOrderWorkflow(OrderState orderState, Date effectiveTime, boolean currentState) {
        orderWorkflows.add(new OrderWorkflow(orderId, orderState, effectiveTime,currentState));
        return this;
    }

    public OrderBuilder addWaitingMinutes(int waitingMinutes) {
        this.waitingMinutes = waitingMinutes;
        return this;
    }

    public OrderBuilder addReceivables(int receivables) {
        this.receivables = receivables;
        return this;
    }

    public Order buildOrder() {
        return new Order(orderId, client, vehicle, addresses, labours, estimate, distance,
                pickupFloors, pickupLiftWorking, dropOffFloors, dropOffLiftWorking, fieldExecutive,
                orderWorkflows, waitingMinutes, receivables);
    }

}
