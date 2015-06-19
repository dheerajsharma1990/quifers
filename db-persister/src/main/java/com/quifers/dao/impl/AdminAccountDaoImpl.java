package com.quifers.dao.impl;

import com.quifers.dao.AdminAccountDao;
import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;

public class AdminAccountDaoImpl implements AdminAccountDao {

    private final DaoWrapper wrapper;

    public AdminAccountDaoImpl(DaoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void saveAdminAccount(AdminAccount adminAccount) {
        wrapper.save(adminAccount);
    }

    @Override
    public AdminAccount getAdminAccount(AdminId adminId) {
        return (AdminAccount) wrapper.get(AdminAccount.class, adminId);
    }
}
