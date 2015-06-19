package com.quifers.hibernate;

import com.quifers.dao.*;
import com.quifers.dao.impl.*;
import org.hibernate.CacheMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DaoFactory {

    private SessionFactory sessionFactory;
    private DaoWrapper daoWrapper;

    public DaoFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        Session session = sessionFactory.openSession();
        session.setCacheMode(CacheMode.IGNORE);
        daoWrapper = new DaoWrapper(session);
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
