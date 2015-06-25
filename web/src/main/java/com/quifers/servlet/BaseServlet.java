package com.quifers.servlet;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.hibernate.DaoFactory;
import com.quifers.servlet.admin.handlers.AdminRequestHandlerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import static com.quifers.servlet.listener.StartupContextListener.DAO_FACTORY;

public class BaseServlet extends HttpServlet {

    private DaoFactory daoFactory;

    protected FieldExecutiveAccountDao fieldExecutiveAccountDao;
    protected FieldExecutiveDao fieldExecutiveDao;
    protected OrderDao orderDao;
    protected AdminRequestHandlerFactory adminRequestHandlerFactory;

    @Override
    public void init() throws ServletException {
        daoFactory = (DaoFactory) getServletContext().getAttribute(DAO_FACTORY);
        fieldExecutiveAccountDao = daoFactory.getFieldExecutiveAccountDao();
        fieldExecutiveDao = daoFactory.getFieldExecutiveDao();
        orderDao = daoFactory.getOrderDao();
        adminRequestHandlerFactory = new AdminRequestHandlerFactory(fieldExecutiveAccountDao, fieldExecutiveDao, orderDao);
    }
}
