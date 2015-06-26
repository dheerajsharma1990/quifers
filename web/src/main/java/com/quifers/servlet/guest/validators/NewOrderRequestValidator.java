package com.quifers.servlet.guest.validators;

import com.quifers.domain.builders.OrderBuilder;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.guest.request.NewOrderRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.apache.commons.lang3.StringUtils.isEmpty;

public class NewOrderRequestValidator implements RequestValidator {

    private final OrderIdGeneratorService orderIdGeneratorService;

    public NewOrderRequestValidator(OrderIdGeneratorService orderIdGeneratorService) {
        this.orderIdGeneratorService = orderIdGeneratorService;
    }

    @Override
    public NewOrderRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {

        OrderBuilder orderBuilder = new OrderBuilder(orderIdGeneratorService.getNewOrderId());

        orderBuilder
                .addClient(validateAndGetClientName(servletRequest), validateAndGetMobileNumber(servletRequest), validateAndGetEmail(servletRequest))
                .addOrderWorkflow(OrderState.BOOKED, validateAndGetBookingDate(servletRequest), true)
                .addVehicle(validateAndGetVehicleName(servletRequest))
                .addAddress(AddressType.PICKUP, emptyCheckValidator(servletRequest, "house_no_pick", "Pick up House Address"),
                        emptyCheckValidator(servletRequest, "society_name_pick", "Pick Up Society Name"),
                        emptyCheckValidator(servletRequest, "area_pick", "Pickup Area"),
                        emptyCheckValidator(servletRequest, "city_pick", "Pickup City"))
                .addAddress(AddressType.DROP, emptyCheckValidator(servletRequest, "house_no_drop", "Drop Off House Address"),
                        emptyCheckValidator(servletRequest, "society_name_drop", "Drop Off Society Name"),
                        emptyCheckValidator(servletRequest, "area_drop", "Drop Off Area"),
                        emptyCheckValidator(servletRequest, "city_drop", "Drop Off City"))
                .addLabours(validateAndGetInteger(servletRequest, "labour"))
                .addEstimate(emptyCheckValidator(servletRequest, "estimate_label", "Estimate"))
                .addPickUpFloors(validateAndGetInteger(servletRequest, "floor_no_pick"))
                .addPickUpLiftWorking(validateAndGetBoolean(servletRequest, "lift_pickup"))
                .addDropOffFloors(validateAndGetInteger(servletRequest, "floor_no_drop"))
                .addDropOffLiftWorking(validateAndGetBoolean(servletRequest, "lift_drop"));

        return new NewOrderRequest(orderBuilder.buildOrder());
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
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
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
