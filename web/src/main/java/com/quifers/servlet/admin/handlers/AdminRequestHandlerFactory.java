package com.quifers.servlet.admin.handlers;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.servlet.CommandNotFoundException;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.RequestHandlerFactory;
import com.quifers.servlet.admin.validators.AssignFieldExecutiveRequestValidator;
import com.quifers.servlet.admin.validators.BookingDateRangeRequestValidator;
import com.quifers.servlet.admin.validators.FieldExecutiveRegisterRequestValidator;
import com.quifers.validations.*;

import javax.servlet.http.HttpServletRequest;

import static com.quifers.servlet.CommandComparator.isEqual;

public class AdminRequestHandlerFactory implements RequestHandlerFactory {

    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private final FieldExecutiveDao fieldExecutiveDao;
    private final OrderDao orderDao;

    public AdminRequestHandlerFactory(FieldExecutiveAccountDao fieldExecutiveAccountDao, FieldExecutiveDao fieldExecutiveDao, OrderDao orderDao) {
        this.fieldExecutiveAccountDao = fieldExecutiveAccountDao;
        this.fieldExecutiveDao = fieldExecutiveDao;
        this.orderDao = orderDao;
    }

    @Override
    public RequestHandler getRequestHandler(HttpServletRequest servletRequest) throws CommandNotFoundException {
        String command = servletRequest.getParameter("cmd");
        if (isEqual("registerFieldExecutive", command)) {
            return new FieldExecutiveRegisterRequestHandler(new FieldExecutiveRegisterRequestValidator(new UserIdAttributeValidator(), new PasswordAttributeValidator(),
                    new NameAttributeValidator(), new MobileNumberAttributeValidator()), fieldExecutiveAccountDao, fieldExecutiveDao);
        } else if (isEqual("getAllFieldExecutives", command)) {
            return new GetAllFieldExecutivesRequestHandler(fieldExecutiveDao);
        } else if (isEqual("assignFieldExecutive", command)) {
            return new AssignFieldExecutiveRequestHandler(new AssignFieldExecutiveRequestValidator(new UserIdAttributeValidator(), new OrderIdAttributeValidator()), fieldExecutiveDao, orderDao);
        } else if (isEqual("unassignedOrders", command)) {
            return new UnassignedOrdersRequestHandler(orderDao);
        } else if (isEqual("assignedOrders", command)) {
            return new AssignedOrdersRequestHandler(new BookingDateRangeRequestValidator(new DayAttributeValidator()), orderDao);
        } else if (isEqual("completedOrders", command)) {
            return new CompletedOrdersRequestHandler(new BookingDateRangeRequestValidator(new DayAttributeValidator()), orderDao);
        }
        throw new CommandNotFoundException(command);
    }

}
