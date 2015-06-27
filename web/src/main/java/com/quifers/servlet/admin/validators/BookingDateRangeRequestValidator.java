package com.quifers.servlet.admin.validators;

import com.quifers.servlet.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.servlet.admin.request.BookingDateRangeRequest;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BookingDateRangeRequestValidator implements RequestValidator {

    public static final String FORMAT = "dd/MM/yyyy";
    private SimpleDateFormat dayFormat = new SimpleDateFormat(FORMAT);

    @Override
    public BookingDateRangeRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        String beginBookingDay = servletRequest.getParameter("begin_booking_day");
        String endBookingDay = servletRequest.getParameter("end_booking_day");
        Date endBookingDate = validateBookingDay(endBookingDay, "End Booking Date");
        endBookingDate = add1Day(endBookingDate);
        return new BookingDateRangeRequest(validateBookingDay(beginBookingDay, "Begin Booking Date"),
                endBookingDate);
    }

    private Date add1Day(Date endBookingDate) {
        Calendar instance = Calendar.getInstance();
        instance.setTime(endBookingDate);
        instance.add(Calendar.DATE, 1);
        return instance.getTime();
    }

    private Date validateBookingDay(String bookingDay, String attributeName) throws InvalidRequestException {
        if (bookingDay == null) {
            throw new InvalidRequestException(attributeName + " is null.");
        }
        try {
            return dayFormat.parse(bookingDay);
        } catch (ParseException e) {
            throw new InvalidRequestException("Invalid " + attributeName + " format.Correct format is " + FORMAT + ".");
        }
    }
}
