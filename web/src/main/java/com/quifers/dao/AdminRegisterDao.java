package com.quifers.dao;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;

import java.sql.Connection;
import java.sql.SQLException;

public class AdminRegisterDao {

    private final Connection connection;
    private final AdminAccountDao adminAccountDao;
    private final AdminDao adminDao;

    public AdminRegisterDao(Connection connection) {
        this.connection = connection;
        this.adminAccountDao = new AdminAccountDao(connection);
        this.adminDao = new AdminDao(connection);
    }

    public void saveAdmin(AdminAccount adminAccount, Admin admin) throws SQLException {
        connection.setAutoCommit(false);
        adminAccountDao.saveAccount(adminAccount);
        adminDao.saveAdmin(admin);
        connection.commit();
        connection.setAutoCommit(true);
    }
}
