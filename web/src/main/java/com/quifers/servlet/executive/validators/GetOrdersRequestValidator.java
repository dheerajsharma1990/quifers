package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.executive.GetOrdersRequest;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.DayAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.validations.UserIdAttributeValidator;

import javax.servlet.http.HttpServletRequest;

public class GetOrdersRequestValidator implements RequestValidator {

    private final UserIdAttributeValidator userIdAttributeValidator;
    private final DayAttributeValidator dayAttributeValidator;

    public GetOrdersRequestValidator(UserIdAttributeValidator userIdAttributeValidator, DayAttributeValidator dayAttributeValidator) {
        this.userIdAttributeValidator = userIdAttributeValidator;
        this.dayAttributeValidator = dayAttributeValidator;
    }

    @Override
    public GetOrdersRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        return new GetOrdersRequest(new FieldExecutiveId(userIdAttributeValidator.validate(getUserId(servletRequest))),
                dayAttributeValidator.validate(getBookingDate(servletRequest)));
    }

    private String getBookingDate(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("booking_date");
    }

    private String getUserId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("user_id");
    }

}
