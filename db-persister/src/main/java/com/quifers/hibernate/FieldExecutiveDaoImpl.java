package com.quifers.hibernate;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collection;

public class FieldExecutiveDaoImpl implements FieldExecutiveDao {

    private final SessionFactory sessionFactory;

    public FieldExecutiveDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveFieldExecutive(FieldExecutive fieldExecutive) {
        if (fieldExecutive != null) {
            Session session = sessionFactory.openSession();
            Transaction transaction = session.beginTransaction();
            session.save(fieldExecutive.getAccount());
            session.save(fieldExecutive);
            transaction.commit();
            session.close();
        }
    }

    @Override
    public FieldExecutive getFieldExecutive(String userId) {
        Session session = sessionFactory.openSession();
        FieldExecutive fieldExecutive = (FieldExecutive) session.get(FieldExecutive.class, userId);
        session.close();
        return fieldExecutive;
    }

    @Override
    public Collection<FieldExecutive> getAllFieldExecutives() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(FieldExecutive.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        Collection<FieldExecutive> fieldExecutives = criteria.list();
        session.close();
        return fieldExecutives;
    }

}
