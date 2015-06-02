package com.quifers.authentication;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;

import java.sql.SQLException;

public class FieldExecutiveAuthenticator {

    private final FieldExecutiveDao fieldExecutiveDao;

    public FieldExecutiveAuthenticator(FieldExecutiveDao fieldExecutiveDao) {
        this.fieldExecutiveDao = fieldExecutiveDao;
    }

    public boolean isValidFieldExecutive(FieldExecutiveAccount fieldExecutiveAccount) throws SQLException {
        FieldExecutive accountFromDb = fieldExecutiveDao.getFieldExecutive(fieldExecutiveAccount.getUserId());
        return accountFromDb != null && accountFromDb.getAccount().equals(fieldExecutiveAccount);
    }
}
