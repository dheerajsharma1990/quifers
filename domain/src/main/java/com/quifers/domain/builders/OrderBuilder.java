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

    public void addClient(String name, long mobileNumber, String email) {
        this.client = new Client(orderId, name, mobileNumber, email);
    }

    public void addVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void addAddress(AddressType addressType, String houseNumber, String society, String city, String area) {
        addresses.add(new Address(orderId, addressType, houseNumber, society, area, city));
    }

    public void addLabours(int labours) {
        this.labours = labours;
    }

    public void addEstimate(String estimate) {
        this.estimate = estimate;
    }

    public void addDistance(int distance) {
        this.distance = distance;
    }

    public void addPickUpFloors(int pickupFloors) {
        this.pickupFloors = pickupFloors;
    }

    public void addPickUpLiftWorking(boolean pickupLiftWorking) {
        this.pickupLiftWorking = pickupLiftWorking;
    }

    public void addDropOffFloors(int dropOffFloors) {
        this.dropOffFloors = dropOffFloors;
    }

    public void addDropOffLiftWorking(boolean dropOffLiftWorking) {
        this.dropOffLiftWorking = dropOffLiftWorking;
    }

    public void addFieldExecutive(String fieldExecutiveId, String name, long mobileNumber) {
        this.fieldExecutive = new FieldExecutive(new FieldExecutiveId(fieldExecutiveId), name, mobileNumber);
    }

    public void addOrderWorkflow(OrderState orderState, Date effectiveTime) {
        orderWorkflows.add(new OrderWorkflow(orderId, orderState, effectiveTime));
    }

    public void addWaitingMinutes(int waitingMinutes) {
        this.waitingMinutes = waitingMinutes;
    }

    public void addReceivables(int receivables) {
        this.receivables = receivables;
    }

    public Order buildOrder() {
        return new Order(orderId, client, vehicle, addresses, labours, estimate, distance,
                pickupFloors, pickupLiftWorking, dropOffFloors, dropOffLiftWorking, fieldExecutive,
                orderWorkflows, waitingMinutes, receivables);
    }

}
