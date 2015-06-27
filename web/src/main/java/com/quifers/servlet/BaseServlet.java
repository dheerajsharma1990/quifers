package com.quifers.servlet;

import com.quifers.authentication.AdminAuthenticator;
import com.quifers.authentication.FieldExecutiveAuthenticator;
import com.quifers.dao.*;
import com.quifers.hibernate.DaoFactory;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.servlet.admin.handlers.AdminRequestHandlerFactory;
import com.quifers.servlet.executive.handlers.FieldExecutiveRequestHandlerFactory;
import com.quifers.servlet.guest.handlers.GuestRequestHandlerFactory;
import com.quifers.servlet.listener.WebPublisher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static com.quifers.servlet.listener.StartupContextListener.DAO_FACTORY;
import static com.quifers.servlet.listener.StartupContextListener.WEB_PUBLISHER;

public class BaseServlet extends HttpServlet {

    private DaoFactory daoFactory;

    protected FieldExecutiveAccountDao fieldExecutiveAccountDao;
    protected FieldExecutiveDao fieldExecutiveDao;
    protected AdminAccountDao adminAccountDao;
    protected AdminDao adminDao;
    protected OrderDao orderDao;
    protected AdminRequestHandlerFactory adminRequestHandlerFactory;
    protected GuestRequestHandlerFactory guestRequestHandlerFactory;
    protected FieldExecutiveRequestHandlerFactory fieldExecutiveRequestHandlerFactory;
    protected OrderIdGeneratorService orderIdGeneratorService;
    protected WebPublisher webPublisher;
    protected AdminAuthenticator adminAuthenticator;
    protected FieldExecutiveAuthenticator fieldExecutiveAuthenticator;

    @Override
    public void init() throws ServletException {
        daoFactory = (DaoFactory) getServletContext().getAttribute(DAO_FACTORY);
        webPublisher = (WebPublisher) getServletContext().getAttribute(WEB_PUBLISHER);
        orderIdGeneratorService = new OrderIdGeneratorService(Long.valueOf(getServletContext().getInitParameter("lastOrderIdCounter")));
        fieldExecutiveAccountDao = daoFactory.getFieldExecutiveAccountDao();
        fieldExecutiveDao = daoFactory.getFieldExecutiveDao();
        orderDao = daoFactory.getOrderDao();
        adminAccountDao = daoFactory.getAdminAccountDao();
        adminDao = daoFactory.getAdminDao();
        adminRequestHandlerFactory = new AdminRequestHandlerFactory(fieldExecutiveAccountDao, fieldExecutiveDao, orderDao);
        adminAuthenticator = new AdminAuthenticator(adminAccountDao);
        fieldExecutiveAuthenticator = new FieldExecutiveAuthenticator(fieldExecutiveAccountDao);
        guestRequestHandlerFactory = new GuestRequestHandlerFactory(adminAccountDao, fieldExecutiveAccountDao, adminDao, orderIdGeneratorService, orderDao, webPublisher);
        fieldExecutiveRequestHandlerFactory = new FieldExecutiveRequestHandlerFactory(orderDao, fieldExecutiveDao, webPublisher);
    }
}
