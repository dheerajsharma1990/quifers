package com.quifers.request;

import com.quifers.request.validators.InvalidRequestException;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FieldExecutiveGetAllOrdersRequest {

    private String bookingDate;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

    public FieldExecutiveGetAllOrdersRequest(HttpServletRequest request) {
        this.bookingDate = request.getParameter("booking_date");
    }

    public Date getBookingDate() throws InvalidRequestException {
        if(bookingDate == null) {
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
