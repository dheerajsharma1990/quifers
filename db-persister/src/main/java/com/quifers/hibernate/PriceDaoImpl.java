package com.quifers.hibernate;

import com.quifers.dao.PriceDao;
import com.quifers.domain.Price;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class PriceDaoImpl implements PriceDao {

    private final SessionFactory sessionFactory;

    public PriceDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void savePrice(Price price) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(price);
        transaction.commit();
        session.close();
    }

    @Override
    public Price getPrice(String orderId) {
        Session session = sessionFactory.openSession();
        Price price = (Price) session.get(Price.class, orderId);
        session.close();
        return price;
    }
}
