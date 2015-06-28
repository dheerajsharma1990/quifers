package com.quifers.request.admin;

import com.quifers.domain.Day;
import com.quifers.servlet.ApiRequest;

public class BookingDateRangeRequest implements ApiRequest {

    private final Day beginBookingDay;
    private final Day endBookingDay;

    public BookingDateRangeRequest(Day beginBookingDay, Day endBookingDay) {
        this.beginBookingDay = beginBookingDay;
        this.endBookingDay = endBookingDay;
    }

    public Day getBeginBookingDay() {
        return beginBookingDay;
    }

    public Day getEndBookingDay() {
        return endBookingDay;
    }
}
