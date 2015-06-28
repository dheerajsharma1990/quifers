package com.quifers.servlet.admin.validators;

import com.quifers.domain.Day;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.admin.request.BookingDateRangeRequest;
import com.quifers.servlet.validations.DayAttributeValidator;
import com.quifers.servlet.validations.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

public class BookingDateRangeRequestValidator implements RequestValidator {

    private final DayAttributeValidator dayAttributeValidator;

    public BookingDateRangeRequestValidator(DayAttributeValidator dayAttributeValidator) {
        this.dayAttributeValidator = dayAttributeValidator;
    }

    @Override
    public BookingDateRangeRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        Day beginBookingDay = dayAttributeValidator.validate(getBeginBookingDay(servletRequest));
        Day endBookingDay = dayAttributeValidator.validate(getEndBookingDay(servletRequest));
        try {
            return new BookingDateRangeRequest(beginBookingDay.getDate(), endBookingDay.add1Day().getDate());
        } catch (ParseException e) {
            throw new InvalidRequestException("An error occurred adding a day to the end booking day.");
        }
    }

    private String getEndBookingDay(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("end_booking_day");
    }

    private String getBeginBookingDay(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("begin_booking_day");
    }
}
