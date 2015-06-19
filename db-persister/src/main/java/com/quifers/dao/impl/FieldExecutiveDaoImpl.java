package com.quifers.dao.impl;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.id.FieldExecutiveId;
import org.hibernate.Criteria;

import java.util.Collection;

public class FieldExecutiveDaoImpl implements FieldExecutiveDao {

    private final DaoWrapper wrapper;

    public FieldExecutiveDaoImpl(DaoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void saveFieldExecutive(FieldExecutive fieldExecutive) {
        if (fieldExecutive != null) {
            wrapper.save(fieldExecutive);
        }
    }

    @Override
    public FieldExecutive getFieldExecutive(FieldExecutiveId fieldExecutiveId) {
        return  (FieldExecutive) wrapper.get(FieldExecutive.class, fieldExecutiveId);
    }

    @Override
    public Collection<FieldExecutive> getAllFieldExecutives() {
        Criteria criteria = wrapper.createCriteria(FieldExecutive.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return wrapper.get(criteria);
    }

}
