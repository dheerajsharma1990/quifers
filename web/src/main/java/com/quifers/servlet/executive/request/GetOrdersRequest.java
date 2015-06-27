package com.quifers.servlet.executive.request;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.ApiRequest;

import java.util.Date;

public class GetOrdersRequest implements ApiRequest {

    private final FieldExecutiveId fieldExecutiveId;
    private final Date bookingDate;

    public GetOrdersRequest(FieldExecutiveId fieldExecutiveId, Date bookingDate) {
        this.fieldExecutiveId = fieldExecutiveId;
        this.bookingDate = bookingDate;
    }

    public FieldExecutiveId getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public Date getBookingDate() {
        return bookingDate;
    }
}
