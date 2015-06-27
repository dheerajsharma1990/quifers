package com.quifers.servlet;

import com.quifers.authentication.AdminAuthenticator;
import com.quifers.authentication.FieldExecutiveAuthenticator;
import com.quifers.dao.*;
import com.quifers.hibernate.DaoFactory;
import com.quifers.service.OrderIdGeneratorService;
import com.quifers.servlet.listener.WebPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.servlet.listener.StartupContextListener.DAO_FACTORY;
import static com.quifers.servlet.listener.StartupContextListener.WEB_PUBLISHER;

public class BaseServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseServlet.class);

    private DaoFactory daoFactory;

    protected FieldExecutiveAccountDao fieldExecutiveAccountDao;
    protected FieldExecutiveDao fieldExecutiveDao;
    protected AdminAccountDao adminAccountDao;
    protected AdminDao adminDao;
    protected OrderDao orderDao;
    protected OrderIdGeneratorService orderIdGeneratorService;
    protected WebPublisher webPublisher;
    protected AdminAuthenticator adminAuthenticator;
    protected FieldExecutiveAuthenticator fieldExecutiveAuthenticator;
    protected FactoryOfRequestHandlers factoryOfRequestHandlers;

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
        adminAuthenticator = new AdminAuthenticator(adminAccountDao);
        fieldExecutiveAuthenticator = new FieldExecutiveAuthenticator(fieldExecutiveAccountDao);
        factoryOfRequestHandlers = new FactoryOfRequestHandlers(adminDao, adminAccountDao, fieldExecutiveAccountDao, fieldExecutiveDao, orderDao, webPublisher, orderIdGeneratorService);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestHandlerFactory requestHandlerFactory = factoryOfRequestHandlers.getRequestHandlerFactory(request.getRequestURI());
            RequestHandler requestHandler = requestHandlerFactory.getRequestHandler(request);
            requestHandler.handleRequest(request, response);
        } catch (CommandNotFoundException e) {
            LOGGER.error("No matching api.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("An error occurred.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }
}
