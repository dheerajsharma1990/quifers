package com.quifers.dao.impl;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Day;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.internal.util.SerializationHelper;

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
        Order order = (Order) wrapper.get(Order.class, orderId);
        return (Order) SerializationHelper.clone(order);
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
    public Collection<Order> getBookedOrders(FieldExecutive fieldExecutive, Day bookingDay) throws Exception {
        Criteria criteria = wrapper.createCriteria(Order.class, "order");
        criteria.createAlias("order.orderWorkflows", "orderWorkflow");
        criteria.add(Restrictions.eq("order.fieldExecutive", fieldExecutive));
        criteria.add(Restrictions.ge("orderWorkflow.effectiveTime", bookingDay.getDate()));
        criteria.add(Restrictions.lt("orderWorkflow.effectiveTime", bookingDay.add1Day().getDate()));
        criteria.add(Restrictions.eq("orderWorkflow.orderWorkflowId.orderState", OrderState.BOOKED));
        criteria.add(Restrictions.eq("orderWorkflow.currentState", true));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return wrapper.get(criteria);
    }

    @Override
    public Collection<Order> getUnassignedOrders() {
        Criteria criteria = wrapper.createCriteria(Order.class, "order");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.isNull("order.fieldExecutive"));
        return wrapper.get(criteria);
    }

    @Override
    public Collection<Order> getOrders(OrderState orderState, Day beginBookingDay, Day endBookingDay) {
        Criteria criteria = wrapper.createCriteria(Order.class, "order");
        criteria.createAlias("order.orderWorkflows", "orderWorkflow");
        criteria.add(Restrictions.ge("orderWorkflow.effectiveTime", beginBookingDay.getDate()));
        criteria.add(Restrictions.lt("orderWorkflow.effectiveTime", endBookingDay.getDate()));
        criteria.add(Restrictions.eq("orderWorkflow.orderWorkflowId.orderState", orderState));
        criteria.add(Restrictions.eq("orderWorkflow.currentState", true));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return wrapper.get(criteria);
    }

}
