package com.quifers.authentication;

import com.quifers.dao.AdminDao;
import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;

import java.sql.SQLException;

public class AdminAuthenticator {

    private final AdminDao adminDao;

    public AdminAuthenticator(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public boolean isValidAdmin(AdminAccount adminAccount) throws SQLException {
        Admin accountFromDb = adminDao.getAdmin(adminAccount.getUserId());
        return accountFromDb != null && accountFromDb.getAccount().equals(adminAccount);
    }
}
