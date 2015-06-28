package com.quifers.request.admin;

import com.quifers.servlet.ApiRequest;

import java.util.Date;

public class BookingDateRangeRequest implements ApiRequest {

    private Date beginBookingDate;
    private Date endBookingDate;

    public BookingDateRangeRequest(Date beginBookingDate, Date endBookingDate) {
        this.beginBookingDate = beginBookingDate;
        this.endBookingDate = endBookingDate;
    }

    public Date getBeginBookingDate() {
        return beginBookingDate;
    }

    public Date getEndBookingDate() {
        return endBookingDate;
    }
}
