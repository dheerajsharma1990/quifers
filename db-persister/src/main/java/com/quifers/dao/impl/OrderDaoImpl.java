package com.quifers.dao.impl;

import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

public class OrderDaoImpl implements OrderDao {

    private final Session session;

    public OrderDaoImpl(Session session) {
        this.session = session;
    }

    @Override
    public void saveOrder(Order order) {
        Transaction transaction = session.beginTransaction();
        session.save(order);
        transaction.commit();
    }

    @Override
    public Order getOrder(String orderId) {
        return  (Order) session.get(Order.class, orderId);
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
        Transaction transaction = session.beginTransaction();
        session.update(order);
        transaction.commit();
    }

    @Override
    public Collection<Order> getOrders(FieldExecutive fieldExecutive) {
        Criteria criteria = session.createCriteria(Order.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("fieldExecutive", fieldExecutive));
        return criteria.list();
    }

    @Override
    public Collection<Order> getAllOrders() {
        Criteria criteria = session.createCriteria(Order.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return criteria.list();
    }

}
