package com.quifers.authentication;

import com.quifers.dao.AdminAccountDao;
import com.quifers.domain.AdminAccount;

import java.sql.SQLException;

public class AdminAuthenticator {

    private final AdminAccountDao adminAccountDao;

    public AdminAuthenticator(AdminAccountDao adminAccountDao) {
        this.adminAccountDao = adminAccountDao;
    }

    public boolean isValidAdmin(AdminAccount adminAccount) throws SQLException {
        AdminAccount accountFromDb = adminAccountDao.getAdminAccount(adminAccount.getAdminId());
        return accountFromDb != null && accountFromDb.equals(adminAccount);
    }
}
