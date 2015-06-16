package com.quifers.dao.impl;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.Collection;

public class FieldExecutiveDaoImpl implements FieldExecutiveDao {

    private final Session session;

    public FieldExecutiveDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void saveFieldExecutive(FieldExecutive fieldExecutive) {
        if (fieldExecutive != null) {
            Transaction transaction = session.beginTransaction();
            session.save(fieldExecutive.getAccount());
            session.save(fieldExecutive);
            transaction.commit();
            session.flush();
        }
    }

    @Override
    public FieldExecutive getFieldExecutive(String userId) {
        return  (FieldExecutive) session.get(FieldExecutive.class, userId);
    }

    @Override
    public Collection<FieldExecutive> getAllFieldExecutives() {
        Criteria criteria = session.createCriteria(FieldExecutive.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

}
