package com.quifers.hibernate;

import com.quifers.dao.AdminDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.dao.impl.AdminDaoImpl;
import com.quifers.dao.impl.DaoWrapper;
import com.quifers.dao.impl.FieldExecutiveDaoImpl;
import com.quifers.dao.impl.OrderDaoImpl;
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

    public AdminDao getAdminDao() {
        return new AdminDaoImpl(daoWrapper);
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
