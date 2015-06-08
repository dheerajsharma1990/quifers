package com.quifers.dao.impl;

import com.quifers.dao.AdminDao;
import com.quifers.domain.Admin;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class AdminDaoImpl implements AdminDao {

    private final Session session;

    public AdminDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void saveAdmin(Admin admin) {
        Transaction transaction = session.beginTransaction();
        session.save(admin.getAccount());
        session.save(admin);
        transaction.commit();
    }

    @Override
    public Admin getAdmin(String userId) {
        return (Admin) session.get(Admin.class, userId);
    }
}
