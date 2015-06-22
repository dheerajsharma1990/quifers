package com.quifers.hibernate;

import com.quifers.dao.*;
import com.quifers.dao.impl.*;
import org.hibernate.SessionFactory;

public class DaoFactory {

    private SessionFactory sessionFactory;
    private DaoWrapper daoWrapper;

    public DaoFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        daoWrapper = new DaoWrapper(sessionFactory);
    }

    public AdminAccountDao getAdminAccountDao() {
        return new AdminAccountDaoImpl(daoWrapper);
    }

    public AdminDao getAdminDao() {
        return new AdminDaoImpl(daoWrapper);
    }

    public FieldExecutiveAccountDao getFieldExecutiveAccountDao() {
        return new FieldExecutiveAccountDaoImpl(daoWrapper);
    }

    public FieldExecutiveDao getFieldExecutiveDao() {
        return new FieldExecutiveDaoImpl(daoWrapper);
    }

    public OrderDao getOrderDao() {
        return new OrderDaoImpl(daoWrapper);
    }

    public void closeDaoFactory() {
        daoWrapper.close();
        sessionFactory.close();
    }

}
