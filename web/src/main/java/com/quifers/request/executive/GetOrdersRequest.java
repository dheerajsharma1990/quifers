package com.quifers.request.executive;

import com.quifers.domain.Day;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.servlet.ApiRequest;

public class GetOrdersRequest implements ApiRequest {

    private final FieldExecutiveId fieldExecutiveId;
    private final Day bookingDay;

    public GetOrdersRequest(FieldExecutiveId fieldExecutiveId, Day bookingDay) {
        this.fieldExecutiveId = fieldExecutiveId;
        this.bookingDay = bookingDay;
    }

    public FieldExecutiveId getFieldExecutiveId() {
        return fieldExecutiveId;
    }

    public Day getBookingDate() {
        return bookingDay;
    }
}
