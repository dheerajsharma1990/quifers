package com.quifers.hibernate;

import com.quifers.dao.AdminDao;
import com.quifers.domain.Admin;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class AdminDaoImpl implements AdminDao {

    private final SessionFactory sessionFactory;

    public AdminDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveAdmin(Admin admin) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(admin.getAccount());
        session.save(admin);
        transaction.commit();
        session.close();
    }

    @Override
    public Admin getAdmin(String userId) {
        Session session = sessionFactory.openSession();
        return (Admin) session.get(Admin.class, userId);
    }
}
