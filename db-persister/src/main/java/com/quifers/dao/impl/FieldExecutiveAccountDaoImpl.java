package com.quifers.dao.impl;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;

public class FieldExecutiveAccountDaoImpl implements FieldExecutiveAccountDao {

    private final DaoWrapper wrapper;

    public FieldExecutiveAccountDaoImpl(DaoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void saveFieldExecutiveAccount(FieldExecutiveAccount fieldExecutiveAccount) throws Exception {
        wrapper.save(fieldExecutiveAccount);
    }

    @Override
    public FieldExecutiveAccount getFieldExecutiveAccount(FieldExecutiveId fieldExecutiveId) {
        return (FieldExecutiveAccount) wrapper.get(FieldExecutiveAccount.class,fieldExecutiveId);
    }
}
