package com.quifers.servlet;

import com.quifers.dao.*;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.servlet.admin.handlers.AdminRequestHandlerFactory;
import com.quifers.servlet.executive.handlers.FieldExecutiveRequestHandlerFactory;
import com.quifers.servlet.guest.handlers.GuestRequestHandlerFactory;
import com.quifers.servlet.listener.WebPublisher;

import static com.quifers.servlet.ApiGroup.ADMIN;
import static com.quifers.servlet.ApiGroup.FIELD_EXECUTIVE;

public class FactoryOfRequestHandlers {

    private final AdminDao adminDao;
    private final AdminAccountDao adminAccountDao;
    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private final FieldExecutiveDao fieldExecutiveDao;
    private final OrderDao orderDao;
    private final WebPublisher webPublisher;
    private final OrderIdGeneratorService orderIdGeneratorService;

    public FactoryOfRequestHandlers(AdminDao adminDao, AdminAccountDao adminAccountDao, FieldExecutiveAccountDao fieldExecutiveAccountDao,
                                    FieldExecutiveDao fieldExecutiveDao, OrderDao orderDao, WebPublisher webPublisher,
                                    OrderIdGeneratorService orderIdGeneratorService) {
        this.adminDao = adminDao;
        this.adminAccountDao = adminAccountDao;
        this.fieldExecutiveAccountDao = fieldExecutiveAccountDao;
        this.fieldExecutiveDao = fieldExecutiveDao;
        this.orderDao = orderDao;
        this.webPublisher = webPublisher;
        this.orderIdGeneratorService = orderIdGeneratorService;
    }

    public RequestHandlerFactory getRequestHandlerFactory(String requestUri) throws CommandNotFoundException {
        ApiGroup apiGroup = ApiGroup.getMatchingApiGroup(requestUri);
        if (ADMIN.equals(apiGroup)) {
            return new AdminRequestHandlerFactory(fieldExecutiveAccountDao, fieldExecutiveDao, orderDao);
        } else if (FIELD_EXECUTIVE.equals(apiGroup)) {
            return new FieldExecutiveRequestHandlerFactory(orderDao, fieldExecutiveDao, webPublisher);
        } else {
            return new GuestRequestHandlerFactory(adminAccountDao, fieldExecutiveAccountDao, adminDao, orderIdGeneratorService, orderDao, webPublisher);
        }
    }
}
