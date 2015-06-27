package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.executive.request.GetOrdersRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GetOrdersRequestValidator implements RequestValidator {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public GetOrdersRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        return new GetOrdersRequest(validateFieldExecutiveId(servletRequest.getParameter("user_id")),
                validateAndGetBookingDate(servletRequest.getParameter("booking_date")));
    }

    private FieldExecutiveId validateFieldExecutiveId(String fieldExecutiveId) throws InvalidRequestException {
        if (fieldExecutiveId == null || fieldExecutiveId.trim().equals("")) {
            throw new InvalidRequestException("Field Executive Id cannot be empty.");
        }

        return new FieldExecutiveId(fieldExecutiveId.trim());
    }

    private Date validateAndGetBookingDate(String bookingDate) throws InvalidRequestException {
        if (bookingDate == null) {
            throw new InvalidRequestException("Null Booking date.Format is dd/MM/yyyy");
        } else {
            try {
                return dateFormat.parse(bookingDate);
            } catch (ParseException e) {
                throw new InvalidRequestException("Invalid Booking date format.Format is dd/MM/yyyy");
            }
        }
    }
}
