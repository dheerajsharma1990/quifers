package com.quifers.dao;

import com.quifers.domain.AdminAccount;
import com.quifers.domain.id.AdminId;

public interface AdminAccountDao {

    void saveAdminAccount(AdminAccount adminAccount) throws Exception;

    AdminAccount getAdminAccount(AdminId adminId);
}
