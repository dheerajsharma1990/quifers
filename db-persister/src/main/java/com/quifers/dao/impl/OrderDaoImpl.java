package com.quifers.dao.impl;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Distance;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

public class OrderDaoImpl implements OrderDao {

    private final DaoWrapper wrapper;

    public OrderDaoImpl(DaoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void addDistance(Distance distance) {
        Order order = getOrder(distance.getOrderId());
        order.setDistance(distance);
        updateOrder(order);
    }

    @Override
    public void saveOrder(Order order) {
        wrapper.save(order);
    }

    @Override
    public Order getOrder(String orderId) {
        return (Order) wrapper.get(Order.class, orderId);
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
        wrapper.update(order);
    }

    @Override
    public Collection<Order> getOrders(FieldExecutive fieldExecutive) {
        Criteria criteria = wrapper.createCriteria(Order.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.eq("fieldExecutive", fieldExecutive));
        return wrapper.get(criteria);
    }

    @Override
    public Collection<Order> getAllOrders() {
        Criteria criteria = wrapper.createCriteria(Order.class);
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return wrapper.get(criteria);
    }

}
