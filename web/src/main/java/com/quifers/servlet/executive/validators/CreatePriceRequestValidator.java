package com.quifers.servlet.executive.validators;

import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import com.quifers.validations.BooleanAttributeValidator;
import com.quifers.validations.InvalidRequestException;
import com.quifers.servlet.RequestValidator;
import com.quifers.request.executive.CreatePriceRequest;
import com.quifers.validations.OrderIdAttributeValidator;
import com.quifers.validations.PositiveIntegerAttributeValidator;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class CreatePriceRequestValidator implements RequestValidator {

    private final OrderIdAttributeValidator orderIdAttributeValidator;
    private final PositiveIntegerAttributeValidator positiveIntegerAttributeValidator;
    private final BooleanAttributeValidator booleanAttributeValidator;

    public CreatePriceRequestValidator(OrderIdAttributeValidator orderIdAttributeValidator, PositiveIntegerAttributeValidator positiveIntegerAttributeValidator,
                                       BooleanAttributeValidator booleanAttributeValidator) {
        this.orderIdAttributeValidator = orderIdAttributeValidator;
        this.positiveIntegerAttributeValidator = positiveIntegerAttributeValidator;
        this.booleanAttributeValidator = booleanAttributeValidator;
    }

    @Override
    public CreatePriceRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {
        OrderId orderId = orderIdAttributeValidator.validate(getOrderId(servletRequest));
        return new CreatePriceRequest(orderId,
                positiveIntegerAttributeValidator.validate(getDistance(servletRequest)),
                positiveIntegerAttributeValidator.validate(getWaitingMinutes(servletRequest)),
                positiveIntegerAttributeValidator.validate(getPickUpFloors(servletRequest)),
                booleanAttributeValidator.validate(getPickUpLiftWorking(servletRequest)),
                positiveIntegerAttributeValidator.validate(getDropOffFloors(servletRequest)),
                booleanAttributeValidator.validate(getDropOffLiftWorking(servletRequest)),
                new OrderWorkflow(orderId, OrderState.COMPLETED, new Date(), true));
    }

    private String getDropOffLiftWorking(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("drop_off_lift_working");
    }

    private String getDropOffFloors(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("drop_off_floors");
    }

    private String getPickUpLiftWorking(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("pick_up_lift_working");
    }

    private String getPickUpFloors(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("pick_up_floors");
    }

    private String getWaitingMinutes(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("waiting_minutes");
    }

    private String getDistance(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("distance");
    }

    private String getOrderId(HttpServletRequest servletRequest) {
        return servletRequest.getParameter("order_id");
    }
}
