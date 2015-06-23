package com.quifers.servlet.admin.request;

import com.quifers.servlet.ApiRequest;

import java.util.Date;

public class CompletedOrdersRequest implements ApiRequest {

    private Date beginBookingDate;
    private Date endBookingDate;

    public CompletedOrdersRequest(Date beginBookingDate, Date endBookingDate) {
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
