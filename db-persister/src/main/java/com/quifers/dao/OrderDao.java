package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;

import java.util.Collection;

public interface OrderDao {

    void saveOrder(Order order);

    Order getOrder(String orderId);

    void addOrderWorkflow(OrderWorkflow orderWorkflow);

    void assignFieldExecutive(String orderId, FieldExecutive fieldExecutive);

    void updateOrder(Order order);

    Collection<Order> getOrders(FieldExecutive fieldExecutive);

    Collection<Order> getAllOrders();

}
