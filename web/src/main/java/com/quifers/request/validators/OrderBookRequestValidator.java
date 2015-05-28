package com.quifers.request.validators;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class OrderBookRequestValidator {

    private final AtomicLong orderIdCounter;

    public OrderBookRequestValidator(AtomicLong orderIdCounter) {
        this.orderIdCounter = orderIdCounter;
    }

    public Order validateRequest(HttpServletRequest request) throws InvalidRequestException {

        String clientName = validateAndGetClientName(request);
        long mobileNumber = validateAndGetMobileNumber(request);
        String email = validateAndGetEmail(request);
        Date bookingDate = validateAndGetBookingDate(request);
        String vehicle = validateAndGetVehicleName(request);

        String fromAddressHouseNumber = emptyCheckValidator(request, "house_no_pick", "Pick up House Address");
        String fromAddressSociety = emptyCheckValidator(request, "society_name_pick", "Pick Up Society Name");
        String fromAddressArea = emptyCheckValidator(request, "area_pick", "Pickup Area");
        String fromAddressCity = emptyCheckValidator(request, "city_pick", "Pickup City");

        String toAddressHouseNumber = emptyCheckValidator(request, "house_no_drop", "Drop Off House Address");
        String toAddressSociety = emptyCheckValidator(request, "society_name_drop", "Drop Off Society Name");
        String toAddressArea = emptyCheckValidator(request, "area_drop", "Drop Off Area");
        String toAddressCity = emptyCheckValidator(request, "city_drop", "Drop Off City");

        int labours = validateAndGetInteger(request, "labour");

        String estimate = emptyCheckValidator(request, "estimate_label", "Estimate");
        String distance = emptyCheckValidator(request, "distance_label", "Distance");

        int pickUpFloors = validateAndGetInteger(request, "floor_no_pick");
        boolean pickupLiftWorking = validateAndGetBoolean(request, "lift_pickup");

        int dropOffFloors = validateAndGetInteger(request, "floor_no_drop");
        boolean dropOffLiftWorking = validateAndGetBoolean(request, "lift_drop");

        long orderId = orderIdCounter.getAndIncrement();

        return new Order(orderId, clientName, mobileNumber, email, vehicle, fromAddressHouseNumber, fromAddressSociety,
                fromAddressArea, fromAddressCity, toAddressHouseNumber, toAddressSociety, toAddressArea, toAddressCity, labours,
                estimate, distance, pickUpFloors, pickupLiftWorking, dropOffFloors, dropOffLiftWorking, null,
                new HashSet<>(Arrays.asList(new OrderWorkflow(orderId, OrderState.BOOKED, bookingDate))));

    }

    private String validateAndGetClientName(HttpServletRequest request) throws InvalidRequestException {
        String clientName = request.getParameter("name_label");
        if (isEmpty(clientName)) {
            throw new InvalidRequestException("Client Name cannot be empty.");
        }
        return clientName.trim();
    }

    private long validateAndGetMobileNumber(HttpServletRequest request) throws InvalidRequestException {
        String mobile = request.getParameter("number_label");
        if (isEmpty(mobile)) {
            throw new InvalidRequestException("Mobile number cannot be empty.");
        }
        mobile = mobile.trim();
        if (mobile.length() != 10) {
            throw new InvalidRequestException("Mobile number must have 10 digits.");
        }
        try {
            return Long.valueOf(mobile);
        } catch (NumberFormatException e) {
            throw new InvalidRequestException("Mobile number must contain all digits.");
        }
    }

    private String validateAndGetEmail(HttpServletRequest request) throws InvalidRequestException {
        String email = request.getParameter("emailid");
        if (isEmpty(email)) {
            throw new InvalidRequestException("Email cannot be empty.");
        }
        return email.trim();
    }

    private Date validateAndGetBookingDate(HttpServletRequest request) throws InvalidRequestException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            return dateFormat.parse(request.getParameter("date_time_label"));
        } catch (ParseException e) {
            throw new InvalidRequestException("Booking date is invalid.");
        }

    }

    private String validateAndGetVehicleName(HttpServletRequest request) throws InvalidRequestException {
        String clientName = request.getParameter("vehicle_list_label");
        if (isEmpty(clientName)) {
            throw new InvalidRequestException("Vehicle Name cannot be empty.");
        }
        return clientName.trim();
    }

    private String emptyCheckValidator(HttpServletRequest request, String attribute, String label) throws InvalidRequestException {
        String value = request.getParameter(attribute);
        if (isEmpty(value)) {
            throw new InvalidRequestException(label + " cannot be empty.");
        }
        return value.trim();
    }

    private int validateAndGetInteger(HttpServletRequest request, String attribute) {
        String value = request.getParameter(attribute);
        if (isEmpty(value)) {
            return 0;
        }
        return Integer.valueOf(value);
    }

    private boolean validateAndGetBoolean(HttpServletRequest request, String attribute) {
        String value = request.getParameter(attribute);
        if (isEmpty(value)) {
            return false;
        }
        return Boolean.valueOf(value);
    }

}
