package com.quifers.dao;

import com.quifers.domain.FieldExecutiveAccount;
import com.quifers.domain.id.FieldExecutiveId;

public interface FieldExecutiveAccountDao {

    void saveFieldExecutiveAccount(FieldExecutiveAccount fieldExecutiveAccount);

    FieldExecutiveAccount getFieldExecutiveAccount(FieldExecutiveId fieldExecutiveId);
}
