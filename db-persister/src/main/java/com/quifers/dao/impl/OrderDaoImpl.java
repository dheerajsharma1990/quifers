package com.quifers.dao.impl;

import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.OrderId;
import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

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
        return new Order(order);
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
    public Collection<Order> getBookedOrders(FieldExecutive fieldExecutive, Date bookingDateTime) throws Exception {
        Criteria criteria = wrapper.createCriteria(Order.class, "order");
        criteria.createAlias("order.orderWorkflows", "orderWorkflow");
        criteria.add(Restrictions.eq("order.fieldExecutive", fieldExecutive));
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String bookingDateAsString = dateFormat.format(bookingDateTime);
        Date startTime = dateTimeFormat.parse(bookingDateAsString + " " + "00:00");
        Date endTime = dateTimeFormat.parse(bookingDateAsString + " " + "23:59");
        criteria.add(Restrictions.between("orderWorkflow.effectiveTime", startTime, endTime));
        criteria.add(Restrictions.eq("orderWorkflow.orderWorkflowId.orderState", OrderState.BOOKED));
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
    public Collection<Order> getAssignedOrders() {
        Criteria criteria = wrapper.createCriteria(Order.class, "order");
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        criteria.add(Restrictions.isNotNull("order.fieldExecutive"));
        return wrapper.get(criteria);
    }

    @Override
    public Collection<Order> getCompletedOrders(Date beginBookingDate, Date endBookingDate) {
        Criteria criteria = wrapper.createCriteria(Order.class, "order");
        criteria.createAlias("order.orderWorkflows", "orderWorkflow");
        criteria.add(Restrictions.between("orderWorkflow.effectiveTime", beginBookingDate, endBookingDate));
        criteria.add(Restrictions.eq("orderWorkflow.orderWorkflowId.orderState", OrderState.COMPLETED));
        criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
        return wrapper.get(criteria);
    }

}
