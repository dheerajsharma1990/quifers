package com.quifers.servlet.guest.validators;

import com.quifers.domain.builders.OrderBuilder;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.enums.OrderState;
import com.quifers.request.guest.NewOrderRequest;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.servlet.RequestValidator;
import com.quifers.validations.*;

import javax.servlet.http.HttpServletRequest;

public class NewOrderRequestValidator implements RequestValidator {

    private final OrderIdGeneratorService orderIdGeneratorService;
    private final MobileNumberAttributeValidator mobileNumberAttributeValidator;
    private final EmptyStringAttributeValidator emptyStringAttributeValidator;
    private final BooleanAttributeValidator booleanAttributeValidator;
    private final PositiveIntegerAttributeValidator positiveIntegerAttributeValidator;
    private final DateAttributeValidator dateAttributeValidator;

    public NewOrderRequestValidator(OrderIdGeneratorService orderIdGeneratorService, MobileNumberAttributeValidator mobileNumberAttributeValidator,
                                    EmptyStringAttributeValidator emptyStringAttributeValidator, BooleanAttributeValidator booleanAttributeValidator,
                                    PositiveIntegerAttributeValidator positiveIntegerAttributeValidator, DateAttributeValidator dateAttributeValidator) {
        this.orderIdGeneratorService = orderIdGeneratorService;
        this.mobileNumberAttributeValidator = mobileNumberAttributeValidator;
        this.emptyStringAttributeValidator = emptyStringAttributeValidator;
        this.booleanAttributeValidator = booleanAttributeValidator;
        this.positiveIntegerAttributeValidator = positiveIntegerAttributeValidator;
        this.dateAttributeValidator = dateAttributeValidator;
    }

    @Override
    public NewOrderRequest validateRequest(HttpServletRequest servletRequest) throws InvalidRequestException {

        OrderBuilder orderBuilder = new OrderBuilder(orderIdGeneratorService.getNewOrderId());

        orderBuilder.addClient(emptyStringAttributeValidator.validate(get(servletRequest, "name_label")),
                mobileNumberAttributeValidator.validate(get(servletRequest, "number_label")),
                emptyStringAttributeValidator.validate(get(servletRequest, "emailid")));
        orderBuilder.addOrderWorkflow(OrderState.BOOKED, dateAttributeValidator.validate(get(servletRequest, "date_time_label")), true);
        orderBuilder.addVehicle(emptyStringAttributeValidator.validate(get(servletRequest, "vehicle_list_label")));
        orderBuilder.addAddress(AddressType.PICKUP, emptyStringAttributeValidator.validate(get(servletRequest, "house_no_pick")),
                emptyStringAttributeValidator.validate(get(servletRequest, "society_name_pick")),
                emptyStringAttributeValidator.validate(get(servletRequest, "area_pick")),
                emptyStringAttributeValidator.validate(get(servletRequest, "city_pick")));
        orderBuilder.addAddress(AddressType.DROP, emptyStringAttributeValidator.validate(get(servletRequest, "house_no_drop")),
                emptyStringAttributeValidator.validate(get(servletRequest, "society_name_drop")),
                emptyStringAttributeValidator.validate(get(servletRequest, "area_drop")),
                emptyStringAttributeValidator.validate(get(servletRequest, "city_drop")));
        orderBuilder.addLabours(positiveIntegerAttributeValidator.validate(get(servletRequest, "labour")));

        orderBuilder.addPickUpFloors(positiveIntegerAttributeValidator.validate(get(servletRequest, "floor_no_pick")));
        orderBuilder.addPickUpLiftWorking(booleanAttributeValidator.validate(get(servletRequest, "lift_pickup")));
        orderBuilder.addDropOffFloors(positiveIntegerAttributeValidator.validate(get(servletRequest, "floor_no_drop")));
        orderBuilder.addDropOffLiftWorking(booleanAttributeValidator.validate(get(servletRequest, "lift_drop")));

        return new NewOrderRequest(orderBuilder.buildOrder());
    }

    private String get(HttpServletRequest servletRequest, String name) {
        return servletRequest.getParameter(name);
    }

}
