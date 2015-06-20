package com.quifers.domain;

import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Order implements Serializable {

    private OrderId orderId;

    private Client client;

    private String vehicle;

    private Set<Address> addresses;

    private int labours;

    private String estimate;

    private Distance distance;

    private int pickupFloors;

    private boolean pickupLiftWorking;

    private int dropOffFloors;

    private boolean dropOffLiftWorking;

    private FieldExecutive fieldExecutive;

    private Set<OrderWorkflow> orderWorkflows;

    public Order() {

    }

    public Order(OrderId orderId, Client client, String vehicle, Set<Address> addresses, int labours, String estimate, Distance distance,
                 int pickupFloors, boolean pickupLiftWorking, int dropOffFloors, boolean dropOffLiftWorking,
                 FieldExecutive fieldExecutive, Set<OrderWorkflow> orderWorkflows) {
        this.orderId = orderId;
        this.client = client;
        this.vehicle = vehicle;
        this.addresses = addresses;
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

    public OrderId getOrderId() {
        return orderId;
    }

    public Client getClient() {
        return client;
    }

    public String getVehicle() {
        return vehicle;
    }

    public Set<Address> getAddresses() {
        return addresses;
    }

    public int getLabours() {
        return labours;
    }

    public String getEstimate() {
        return estimate;
    }

    public Distance getDistance() {
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

    public Set<OrderWorkflow> getOrderWorkflows() {
        return orderWorkflows;
    }

    public void setOrderId(OrderId orderId) {
        this.orderId = orderId;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public void setVehicle(String vehicle) {
        this.vehicle = vehicle;
    }

    public void setAddresses(Set<Address> addresses) {
        this.addresses = addresses;
    }

    public void setLabours(int labours) {
        this.labours = labours;
    }

    public void setEstimate(String estimate) {
        this.estimate = estimate;
    }

    public void setDistance(Distance distance) {
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

    public Cost getCost() {
        int transitCost = getTransitCost();
        int waitingCost = getWaitingCost();
        int labourCost = getLabourCost();
        return new Cost(orderId, transitCost, waitingCost, labourCost);
    }

    private int getWaitingCost() {
        int waitingMinutes = getWaitingMinutes();
        if (waitingMinutes <= 60) {
            return 0;
        }
        int remainingMinutes = waitingMinutes - 60;
        int roundOff = remainingMinutes % 15;
        int minutesBlock = remainingMinutes / 15 + (roundOff <= 10 ? 0 : 1);
        return minutesBlock * 50;
    }

    private int getTransitCost() {
        if (distance.getDistance() <= 2) {
            return 300;
        }
        return 300 + (distance.getDistance() - 2) * 30;
    }

    private int getLabourCost() {
        int nonWorkingLifts = getNonWorkingPickUpFloors() + getNonWorkingDropOffFloors();
        if (nonWorkingLifts <= 2) {
            return 300 * labours;
        }
        return 350 * (nonWorkingLifts - 2);
    }

    private int getWaitingMinutes() {
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
            if (orderState.equals(workflow.getOrderWorkflowId().getOrderState())) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Order order = (Order) o;

        if (dropOffFloors != order.dropOffFloors) return false;
        if (dropOffLiftWorking != order.dropOffLiftWorking) return false;
        if (labours != order.labours) return false;
        if (pickupFloors != order.pickupFloors) return false;
        if (pickupLiftWorking != order.pickupLiftWorking) return false;
        if (addresses != null ? !addresses.equals(order.addresses) : order.addresses != null) return false;
        if (client != null ? !client.equals(order.client) : order.client != null) return false;
        if (distance != null ? !distance.equals(order.distance) : order.distance != null) return false;
        if (estimate != null ? !estimate.equals(order.estimate) : order.estimate != null) return false;
        if (fieldExecutive != null ? !fieldExecutive.equals(order.fieldExecutive) : order.fieldExecutive != null)
            return false;
        if (orderId != null ? !orderId.equals(order.orderId) : order.orderId != null) return false;
        if (orderWorkflows != null ? !orderWorkflows.equals(order.orderWorkflows) : order.orderWorkflows != null)
            return false;
        if (vehicle != null ? !vehicle.equals(order.vehicle) : order.vehicle != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return orderId != null ? orderId.hashCode() : 0;
    }

    public int getNonWorkingDropOffFloors() {
        if (dropOffLiftWorking) {
            return dropOffFloors;
        }
        return 0;
    }

    public Address getPickUpAddress() {
        for (Address address : addresses) {
            if (AddressType.PICKUP.equals(address.getAddressId().getAddressType())) {
                return address;
            }
        }
        return null;
    }

    public Address getDropOffAddress() {
        for (Address address : addresses) {
            if (AddressType.DROP.equals(address.getAddressId().getAddressType())) {
                return address;
            }
        }
        return null;
    }


    @Override
    public String toString() {
        return ReflectionToStringBuilder.reflectionToString(this, ToStringStyle.JSON_STYLE);
    }
}
