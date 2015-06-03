package com.quifers.domain;

import com.quifers.domain.enums.OrderState;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Order implements Serializable {

    private long orderId;

    private String name;

    private long mobileNumber;

    private String email;

    private String vehicle;

    private String fromAddressHouseNumber;

    private String fromAddressSociety;

    private String fromAddressArea;

    private String fromAddressCity;

    private String toAddressHouseNumber;

    private String toAddressSociety;

    private String toAddressArea;

    private String toAddressCity;

    private int labours;

    private String estimate;

    private int distance;

    private int pickupFloors;

    private boolean pickupLiftWorking;

    private int dropOffFloors;

    private boolean dropOffLiftWorking;

    private FieldExecutive fieldExecutive;

    private Set<OrderWorkflow> orderWorkflows;

    public Order() {

    }

    public Order(long orderId, String name, long mobileNumber, String email, String vehicle, String fromAddressHouseNumber,
                 String fromAddressSociety, String fromAddressArea, String fromAddressCity, String toAddressHouseNumber,
                 String toAddressSociety, String toAddressArea, String toAddressCity, int labours, String estimate,
                 int distance, int pickupFloors, boolean pickupLiftWorking, int dropOffFloors, boolean dropOffLiftWorking,
                 FieldExecutive fieldExecutive, Set<OrderWorkflow> orderWorkflows) {
        this.orderId = orderId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.vehicle = vehicle;
        this.fromAddressHouseNumber = fromAddressHouseNumber;
        this.fromAddressSociety = fromAddressSociety;
        this.fromAddressArea = fromAddressArea;
        this.fromAddressCity = fromAddressCity;
        this.toAddressHouseNumber = toAddressHouseNumber;
        this.toAddressSociety = toAddressSociety;
        this.toAddressArea = toAddressArea;
        this.toAddressCity = toAddressCity;
        this.labours = labours;
        this.estimate = estimate;
        this.distance = distance;
        this.pickupFloors = pickupFloors;
        this.pickupLiftWorking = pickupLiftWorking;
        this.dropOffFloors = dropOffFloors;
        this.dropOffLiftWorking = dropOffLiftWorking;
        this.fieldExecutive = fieldExecutive;
        this.orderWorkflows = orderWorkflows;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getName() {
        return name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getVehicle() {
        return vehicle;
    }

    public String getFromAddressHouseNumber() {
        return fromAddressHouseNumber;
    }

    public String getFromAddressSociety() {
        return fromAddressSociety;
    }

    public String getFromAddressArea() {
        return fromAddressArea;
    }

    public String getFromAddressCity() {
        return fromAddressCity;
    }

    public String getToAddressHouseNumber() {
        return toAddressHouseNumber;
    }

    public String getToAddressSociety() {
        return toAddressSociety;
    }

    public String getToAddressArea() {
        return toAddressArea;
    }

    public String getToAddressCity() {
        return toAddressCity;
    }

    public int getLabours() {
        return labours;
    }

    public String getEstimate() {
        return estimate;
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

    public FieldExecutive getFieldExecutive() {
        return fieldExecutive;
    }

    public Collection<OrderWorkflow> getOrderWorkflows() {
        return orderWorkflows;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMobileNumber(long mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setFromAddressHouseNumber(String fromAddressHouseNumber) {
        this.fromAddressHouseNumber = fromAddressHouseNumber;
    }

    public void setFromAddressSociety(String fromAddressSociety) {
        this.fromAddressSociety = fromAddressSociety;
    }

    public void setFromAddressArea(String fromAddressArea) {
        this.fromAddressArea = fromAddressArea;
    }

    public void setFromAddressCity(String fromAddressCity) {
        this.fromAddressCity = fromAddressCity;
    }

    public void setToAddressHouseNumber(String toAddressHouseNumber) {
        this.toAddressHouseNumber = toAddressHouseNumber;
    }

    public void setToAddressSociety(String toAddressSociety) {
        this.toAddressSociety = toAddressSociety;
    }

    public void setToAddressArea(String toAddressArea) {
        this.toAddressArea = toAddressArea;
    }

    public void setToAddressCity(String toAddressCity) {
        this.toAddressCity = toAddressCity;
    }

    public void setLabours(int labours) {
        this.labours = labours;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public void setPickupFloors(int pickupFloors) {
        this.pickupFloors = pickupFloors;
    }

    public void setPickupLiftWorking(boolean pickupLiftWorking) {
        this.pickupLiftWorking = pickupLiftWorking;
    }

    public void setDropOffFloors(int dropOffFloors) {
        this.dropOffFloors = dropOffFloors;
    }

    public void setDropOffLiftWorking(boolean dropOffLiftWorking) {
        this.dropOffLiftWorking = dropOffLiftWorking;
    }

    public void setFieldExecutive(FieldExecutive fieldExecutive) {
        this.fieldExecutive = fieldExecutive;
    }

    public void setOrderWorkflows(Set<OrderWorkflow> orderWorkflows) {
        this.orderWorkflows = orderWorkflows;
    }

    public void addOrderWorkflow(OrderWorkflow orderWorkflow) {
        if (orderWorkflows == null) {
            orderWorkflows = new HashSet<>();
        }
        this.orderWorkflows.add(orderWorkflow);
    }

    public int getWaitingMinutes() {
        OrderWorkflow tripStarted = getWorkflow(OrderState.TRIP_STARTED);
        OrderWorkflow transitStarted = getWorkflow(OrderState.TRANSIT_STARTED);
        OrderWorkflow transitEnded = getWorkflow(OrderState.TRANSIT_ENDED);
        OrderWorkflow tripEnded = getWorkflow(OrderState.TRIP_ENDED);
        long startTripTime = tripStarted.getEffectiveTime().getTime();
        long startTransitTime = transitStarted.getEffectiveTime().getTime();
        long endTransitTime = transitEnded.getEffectiveTime().getTime();
        long endTripTime = tripEnded.getEffectiveTime().getTime();
        return (int) (((startTransitTime - startTripTime) + (endTripTime - endTransitTime)) / (1000 * 60));
    }

    public OrderWorkflow getWorkflow(OrderState orderState) {
        for (OrderWorkflow workflow : orderWorkflows) {
            if (orderState.equals(workflow.getOrderState())) {
                return workflow;
            }
        }
        return null;
    }

    public int getNonWorkingPickUpFloors() {
        if (pickupLiftWorking) {
            return pickupFloors;
        }
        return 0;
    }

    public int getNonWorkingDropOffFloors() {
        if (dropOffLiftWorking) {
            return dropOffFloors;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (dropOffFloors != order.dropOffFloors) return false;
        if (dropOffLiftWorking != order.dropOffLiftWorking) return false;
        if (labours != order.labours) return false;
        if (mobileNumber != order.mobileNumber) return false;
        if (orderId != order.orderId) return false;
        if (pickupFloors != order.pickupFloors) return false;
        if (pickupLiftWorking != order.pickupLiftWorking) return false;
        if (distance != order.distance) return false;
        if (email != null ? !email.equals(order.email) : order.email != null) return false;
        if (estimate != null ? !estimate.equals(order.estimate) : order.estimate != null) return false;
        if (fieldExecutive != null ? !fieldExecutive.equals(order.fieldExecutive) : order.fieldExecutive != null)
            return false;
        if (fromAddressArea != null ? !fromAddressArea.equals(order.fromAddressArea) : order.fromAddressArea != null)
            return false;
        if (fromAddressCity != null ? !fromAddressCity.equals(order.fromAddressCity) : order.fromAddressCity != null)
            return false;
        if (fromAddressHouseNumber != null ? !fromAddressHouseNumber.equals(order.fromAddressHouseNumber) : order.fromAddressHouseNumber != null)
            return false;
        if (fromAddressSociety != null ? !fromAddressSociety.equals(order.fromAddressSociety) : order.fromAddressSociety != null)
            return false;
        if (name != null ? !name.equals(order.name) : order.name != null) return false;
        if (orderWorkflows != null ? !orderWorkflows.equals(order.orderWorkflows) : order.orderWorkflows != null)
            return false;
        if (toAddressArea != null ? !toAddressArea.equals(order.toAddressArea) : order.toAddressArea != null)
            return false;
        if (toAddressCity != null ? !toAddressCity.equals(order.toAddressCity) : order.toAddressCity != null)
            return false;
        if (toAddressHouseNumber != null ? !toAddressHouseNumber.equals(order.toAddressHouseNumber) : order.toAddressHouseNumber != null)
            return false;
        if (toAddressSociety != null ? !toAddressSociety.equals(order.toAddressSociety) : order.toAddressSociety != null)
            return false;
        if (vehicle != null ? !vehicle.equals(order.vehicle) : order.vehicle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return (int) (orderId ^ (orderId >>> 32));
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this);
    }
}
