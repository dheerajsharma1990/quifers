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
        String fromAddress = validateAndGetFromAddress(request);
        String toAddress = validateAndGetToAddress(request);
        Date bookingDate = validateAndGetBookingDate(request);
        long orderId = orderIdCounter.getAndIncrement();
        return new Order(orderId, clientName, mobileNumber, email, fromAddress, toAddress, null, new HashSet<>(Arrays.asList(new OrderWorkflow(orderId, OrderState.BOOKED, bookingDate))));
    }

    private String validateAndGetClientName(HttpServletRequest request) throws InvalidRequestException {
        String clientName = request.getParameter("client_name");
        if (isEmpty(clientName)) {
            throw new InvalidRequestException("Client Name cannot be empty.");
        }
        return clientName.trim();
    }

    private long validateAndGetMobileNumber(HttpServletRequest request) throws InvalidRequestException {
        String mobile = request.getParameter("mobile_number");
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
        String email = request.getParameter("email");
        if (isEmpty(email)) {
            throw new InvalidRequestException("Email cannot be empty.");
        }
        return email.trim();
    }

    private String validateAndGetFromAddress(HttpServletRequest request) throws InvalidRequestException {
        String fromAddress = request.getParameter("from_address");
        if (isEmpty(fromAddress)) {
            throw new InvalidRequestException("From Address cannot be empty.");
        }
        return fromAddress.trim();
    }

    private String validateAndGetToAddress(HttpServletRequest request) throws InvalidRequestException {
        String toAddress = request.getParameter("to_address");
        if (isEmpty(toAddress)) {
            throw new InvalidRequestException("To Address cannot be empty.");
        }
        return toAddress.trim();
    }

    private Date validateAndGetBookingDate(HttpServletRequest request) throws InvalidRequestException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
        try {
            return dateFormat.parse(request.getParameter("booking_date"));
        } catch (ParseException e) {
            throw new InvalidRequestException("Booking date is invalid.");
        }

    }

}
