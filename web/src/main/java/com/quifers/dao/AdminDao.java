package com.quifers.dao;

import com.quifers.domain.Admin;

public interface AdminDao {

    void saveAdmin(Admin admin);

    Admin getAdmin(String userId);
}
