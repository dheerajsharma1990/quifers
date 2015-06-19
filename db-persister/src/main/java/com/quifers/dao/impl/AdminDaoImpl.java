package com.quifers.dao.impl;

import com.quifers.dao.AdminDao;
import com.quifers.domain.Admin;

public class AdminDaoImpl implements AdminDao {

    private final DaoWrapper wrapper;

    public AdminDaoImpl(DaoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void saveAdmin(Admin admin) throws Exception {
        wrapper.save(admin);
    }

    @Override
    public Admin getAdmin(String userId) {
        return (Admin) wrapper.get(Admin.class, userId);
    }
}
