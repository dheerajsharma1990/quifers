package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.id.OrderId;

import java.util.Collection;
import java.util.Date;

public interface OrderDao {

    void saveOrder(Order order) throws Exception;

    Order getOrder(OrderId orderId);

    void assignFieldExecutive(OrderId orderId, FieldExecutive fieldExecutive) throws Exception;

    void updateOrder(Order order) throws Exception;

    Collection<Order> getBookedOrders(FieldExecutive fieldExecutive, Date bookingDateTime) throws Exception;

    Collection<Order> getUnassignedOrders();

    Collection<Order> getAssignedOrders(Date beginBookingDate, Date endBookingDate);

    Collection<Order> getCompletedOrders(Date beginBookingDate,Date endBookingDate);

}
