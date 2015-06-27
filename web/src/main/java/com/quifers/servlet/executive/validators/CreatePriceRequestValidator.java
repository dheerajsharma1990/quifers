package com.quifers.servlet.executive.validators;

import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.executive.request.CreatePriceRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreatePriceRequestValidator implements RequestValidator {

    @Override
    public CreatePriceRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        OrderId orderId = validateAndGetOrderId(servletRequest.getParameter("order_id"));
        return new CreatePriceRequest(orderId,
                validateAndGetDistance(servletRequest.getParameter("distance")),
                validateAndGetWaitingMinutes(servletRequest.getParameter("waiting_minutes")),
                validateAndGetPickupFloors(servletRequest.getParameter("pick_up_floors")),
                validateAndGetPickupLiftWorking(servletRequest.getParameter("pick_up_lift_working")),
                validateAndGetPickupFloors(servletRequest.getParameter("drop_off_floors")),
                validateAndGetPickupLiftWorking(servletRequest.getParameter("drop_off_lift_working")),
                new OrderWorkflow(orderId, OrderState.COMPLETED, new Date(), true));
    }

    private OrderId validateAndGetOrderId(String orderId) throws InvalidRequestException {
        if (orderId == null || orderId.trim().equals("")) {
            throw new InvalidRequestException("Order Id cannot be empty.");
        }
        return new OrderId(orderId.trim());
    }

    private int validateAndGetDistance(String distance) throws InvalidRequestException {
        if (distance == null || distance.trim().equals("")) {
            throw new InvalidRequestException("Distance cannot be empty.");
        }
        try {
            Integer integer = Integer.valueOf(distance);
            if (integer < 0) {
                throw new InvalidRequestException("Distance must be greater then or equal to 0.");
            }
            return integer;
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid value of distance entered.");
        }
    }

    private int validateAndGetWaitingMinutes(String waitingMinutes) throws InvalidRequestException {
        if (waitingMinutes == null || waitingMinutes.trim().equals("")) {
            throw new InvalidRequestException("Waiting Minutes cannot be empty.");
        }
        try {
            Integer integer = Integer.valueOf(waitingMinutes);
            if (integer < 0) {
                throw new InvalidRequestException("Waiting Minutes must be greater then or equal to 0.");
            }
            return integer;
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid value of Waiting Minutes entered.");
        }
    }

    private int validateAndGetPickupFloors(String pickUpFloors) throws InvalidRequestException {
        if (pickUpFloors == null || pickUpFloors.trim().equals("")) {
            throw new InvalidRequestException("Pick up floors cannot be empty.");
        }
        try {
            Integer integer = Integer.valueOf(pickUpFloors);
            if (integer < 0) {
                throw new InvalidRequestException("Pick up floors must be greater then or equal to 0.");
            }
            return integer;
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid value of pick up floors entered.");
        }
    }

    private boolean validateAndGetPickupLiftWorking(String pickupLiftWorking) throws InvalidRequestException {
        if (pickupLiftWorking == null || pickupLiftWorking.trim().equals("")) {
            throw new InvalidRequestException("Pick up lift working cannot be empty.");
        }
        pickupLiftWorking = pickupLiftWorking.toLowerCase();
        if (pickupLiftWorking.equals("false") || pickupLiftWorking.equals("true")) {
            return Boolean.valueOf(pickupLiftWorking);
        }

        throw new InvalidRequestException("Invalid value of Pick Up Lift Working");
    }

    private int validateAndGetDropOffFloors(String dropOffFloors) throws InvalidRequestException {
        if (dropOffFloors == null || dropOffFloors.trim().equals("")) {
            throw new InvalidRequestException("Drop off floors cannot be empty.");
        }
        try {
            Integer integer = Integer.valueOf(dropOffFloors);
            if (integer < 0) {
                throw new InvalidRequestException("Drop off floors must be greater then or equal to 0.");
            }
            return integer;
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Invalid value of drop off floors entered.");
        }
    }

    private boolean validateAndGetDropOffLiftWorking(String dropOffLiftWorking) throws InvalidRequestException {
        if (dropOffLiftWorking == null || dropOffLiftWorking.trim().equals("")) {
            throw new InvalidRequestException("Pick up lift working cannot be empty.");
        }
        dropOffLiftWorking = dropOffLiftWorking.toLowerCase();
        if (dropOffLiftWorking.equals("false") || dropOffLiftWorking.equals("true")) {
            return Boolean.valueOf(dropOffLiftWorking);
        }

        throw new InvalidRequestException("Invalid value of Drop Off Lift Working");
    }

}
