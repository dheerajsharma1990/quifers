package com.quifers.hibernate;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OrderDaoImpl implements OrderDao {

    private final SessionFactory sessionFactory;
    private final FieldExecutiveDao fieldExecutiveDao;

    public OrderDaoImpl(SessionFactory sessionFactory, FieldExecutiveDao fieldExecutiveDao) {
        this.sessionFactory = sessionFactory;
        this.fieldExecutiveDao = fieldExecutiveDao;
    }

    @Override
    public void saveOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        fieldExecutiveDao.saveFieldExecutive(order.getFieldExecutive());
        session.save(order);
        transaction.commit();
        session.close();
    }

    @Override
    public Order getOrder(long orderId) {
        Session session = sessionFactory.openSession();
        Order order = (Order) session.get(Order.class, orderId);
        session.close();
        return order;
    }

    @Override
    public void addOrderWorkflow(OrderWorkflow orderWorkflow) {
        Order order = getOrder(orderWorkflow.getOrderId());
        order.addOrderWorkflow(orderWorkflow);
        updateOrder(order);
    }

    @Override
    public void assignFieldExecutive(long orderId, String fieldExecutiveId) {
        FieldExecutive fieldExecutive = fieldExecutiveDao.getFieldExecutive(fieldExecutiveId);
        Order order = getOrder(orderId);
        order.setFieldExecutive(fieldExecutive);
        updateOrder(order);
    }

    private void updateOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }

}
