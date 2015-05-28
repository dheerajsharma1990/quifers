package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.FieldExecutiveAccount;

import java.sql.Connection;
import java.sql.SQLException;

public class FieldExecutiveRegisterDao {

    private final Connection connection;
    private final FieldExecutiveAccountDao fieldExecutiveAccountDao;
    private final FieldExecutiveDao fieldExecutiveDao;

    public FieldExecutiveRegisterDao(Connection connection) {
        this.connection = connection;
        fieldExecutiveAccountDao = new FieldExecutiveAccountDao(connection);
        fieldExecutiveDao = new FieldExecutiveDao(connection);
    }

    public void saveFieldExecutive(FieldExecutiveAccount account, FieldExecutive fieldExecutive) throws SQLException {
        connection.setAutoCommit(false);
        fieldExecutiveAccountDao.saveAccount(account);
        fieldExecutiveDao.saveFieldExecutive(fieldExecutive);
        connection.commit();
        connection.setAutoCommit(true);
    }
}
