package com.quifers.hibernate;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class FieldExecutiveDaoImpl implements FieldExecutiveDao {

    private final SessionFactory sessionFactory;

    public FieldExecutiveDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void saveFieldExecutive(FieldExecutive fieldExecutive) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(fieldExecutive.getAccount());
        session.save(fieldExecutive);
        transaction.commit();
        session.close();
    }

    public FieldExecutive getFieldExecutive(String userId) {
        Session session = sessionFactory.openSession();
        return (FieldExecutive) session.get(FieldExecutive.class, userId);
    }

    public Collection<FieldExecutive> getAllFieldExecutives() {
        Session session = sessionFactory.openSession();
        return session.createCriteria(FieldExecutive.class).list();
    }

}
