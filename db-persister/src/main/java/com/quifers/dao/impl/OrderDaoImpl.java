package com.quifers.dao.impl;

import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.id.OrderId;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.util.Collection;

public class OrderDaoImpl implements OrderDao {

    private final DaoWrapper wrapper;

    public OrderDaoImpl(DaoWrapper wrapper) {
        this.wrapper = wrapper;
    }

    @Override
    public void saveOrder(Order order) throws Exception {
        wrapper.save(order);
    }

    @Override
    public Order getOrder(OrderId orderId) {
        return (Order) wrapper.get(Order.class, orderId);
    }

    @Override
    public void addOrderWorkflow(OrderWorkflow orderWorkflow) throws Exception {
        Order order = getOrder(new OrderId(orderWorkflow.getOrderWorkflowId().getOrderId()));
        order.addOrderWorkflow(orderWorkflow);
        updateOrder(order);
    }

    @Override
    public void assignFieldExecutive(OrderId orderId, FieldExecutive fieldExecutive) throws Exception {
        Order order = getOrder(orderId);
        order.setFieldExecutive(fieldExecutive);
        updateOrder(order);
    }

    @Override
    public void updateOrder(Order order) throws Exception {
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
