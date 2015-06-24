package com.quifers.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.List;

public class DaoWrapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(DaoWrapper.class);
    private final SessionFactory sessionFactory;

    public DaoWrapper(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    public void save(Object object) throws Exception {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("An exception occurred in saving object to database.", e);
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
            throw new DatabasePersistenceException(e);
        }
    }

    public void update(Object object) throws Exception {
        Transaction transaction = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();
            session.merge(object);
            transaction.commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
            if (session != null) {
                session.close();
            }
            throw new Exception(e);
        }
    }

    public Criteria createCriteria(Class clazz) {
        Session session = sessionFactory.openSession();
        return session.createCriteria(clazz);
    }

    public Criteria createCriteria(Class clazz, String alias) {
        Session session = sessionFactory.openSession();
        return session.createCriteria(clazz, alias);
    }

    public Object get(Class clazz, Serializable object) {
        Session session = sessionFactory.openSession();
        Object o = session.get(clazz, object);
        session.close();
        return o;
    }

    public List get(Criteria criteria) {
        Session session = sessionFactory.openSession();
        List list = criteria.list();
        session.close();
        return list;
    }

    public void close() {
        sessionFactory.close();
    }
}
