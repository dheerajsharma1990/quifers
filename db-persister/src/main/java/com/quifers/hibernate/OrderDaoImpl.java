package com.quifers.hibernate;

import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import org.hibernate.*;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;
import java.util.List;

public class OrderDaoImpl implements OrderDao {

    private final SessionFactory sessionFactory;

    public OrderDaoImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public void saveOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
        session.close();
    }

    @Override
    public Order getOrder(String orderId) {
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
    public void assignFieldExecutive(String orderId, FieldExecutive fieldExecutive) {
        Order order = getOrder(orderId);
        order.setFieldExecutive(fieldExecutive);
        updateOrder(order);
    }

    @Override
    public void updateOrder(Order order) {
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
        session.close();
    }

    @Override
    public Collection<Order> getOrders(FieldExecutive fieldExecutive) {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("fieldExecutive", fieldExecutive));
        List list = criteria.list();
        session.close();
        return list;
    }

    @Override
    public Collection<Order> getAllOrders() {
        Session session = sessionFactory.openSession();
        Criteria criteria = session.createCriteria(Order.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        List list = criteria.list();
        session.close();
        return list;
    }

}
