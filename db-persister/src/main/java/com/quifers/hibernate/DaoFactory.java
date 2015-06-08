package com.quifers.hibernate;

import com.quifers.dao.AdminDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.dao.impl.AdminDaoImpl;
import com.quifers.dao.impl.FieldExecutiveDaoImpl;
import com.quifers.dao.impl.OrderDaoImpl;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class DaoFactory {

    private SessionFactory sessionFactory;
    private Session session;

    public DaoFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        this.session = sessionFactory.openSession();
    }

    public AdminDao getAdminDao() {
        return new AdminDaoImpl(session);
    }

    public FieldExecutiveDao getFieldExecutiveDao() {
        return new FieldExecutiveDaoImpl(session);
    }

    public OrderDao getOrderDao() {
        return new OrderDaoImpl(session);
    }

    public void closeDaoFactory() {
        session.close();
        sessionFactory.close();
    }

}
