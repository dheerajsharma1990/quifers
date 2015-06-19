package com.quifers.authentication;

import com.quifers.dao.FieldExecutiveAccountDao;
import com.quifers.domain.FieldExecutiveAccount;

import java.sql.SQLException;

public class FieldExecutiveAuthenticator {

    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;

    public FieldExecutiveAuthenticator(FieldExecutiveAccountDao fieldExecutiveAccountDao) {
        this.fieldExecutiveAccountDao = fieldExecutiveAccountDao;
    }

    public boolean isValidFieldExecutive(FieldExecutiveAccount fieldExecutiveAccount) throws SQLException {
        FieldExecutiveAccount accountFromDb = fieldExecutiveAccountDao.getFieldExecutiveAccount(fieldExecutiveAccount.getFieldExecutiveId());
        return accountFromDb != null && accountFromDb.equals(fieldExecutiveAccount);
    }
}
