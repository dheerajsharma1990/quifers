package com.quifers.dao.impl;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public class DaoWrapper {

    private final Session session;

    public DaoWrapper(Session session) {
        this.session = session;
    }

    public void save(Object object) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public void update(Object object) {
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
        } catch (Exception e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    public Criteria createCriteria(Class clazz) {
        return session.createCriteria(clazz);
    }

    public Object get(Class clazz, Serializable object) {
        session.clear();
        Object o = session.get(clazz, object);
        return o;
    }

    public List get(Criteria criteria) {
        session.clear();
        List list = criteria.list();
        return list;
    }

    public void close() {
        session.close();
    }
}