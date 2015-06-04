package com.quifers.dao;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;

public interface OrderDao {

    void saveOrder(Order order);

    Order getOrder(long orderId);

    void addOrderWorkflow(OrderWorkflow orderWorkflow);

    void assignFieldExecutive(long orderId, FieldExecutive fieldExecutive);
}
