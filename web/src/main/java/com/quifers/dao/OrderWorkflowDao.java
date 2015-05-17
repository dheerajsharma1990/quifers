package com.quifers.dao;

import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;

import java.sql.*;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrderWorkflowDao {

    private final Connection connection;

    private final String TABLE_NAME = "orders_workflow";
    private final String ORDER_ID_COLUMN = "order_id";
    private final String ORDER_STATE_COLUMN = "order_state";
    private final String EFFECTIVE_TIME_COLUMN = "effective_time";

    private String allColumns = ORDER_ID_COLUMN + "," + ORDER_STATE_COLUMN + "," + EFFECTIVE_TIME_COLUMN;

    public OrderWorkflowDao(Connection connection) {
        this.connection = connection;
    }

    public int[] saveOrderWorkflows(Collection<OrderWorkflow> orderWorkflows) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" + allColumns + ")" + " " + "VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for(OrderWorkflow orderWorkflow : orderWorkflows) {
            preparedStatement.setLong(1, orderWorkflow.getOrderId());
            preparedStatement.setString(2, orderWorkflow.getOrderState().name());
            preparedStatement.setTimestamp(3, new Timestamp(orderWorkflow.getEffectiveTime().getTime()));
            preparedStatement.addBatch();
        }
        return preparedStatement.executeBatch();
    }

    public Collection<OrderWorkflow> getOrderWorkflows(long orderId) throws SQLException {
        String sql = "SELECT" + " " + allColumns + " FROM " + TABLE_NAME + " WHERE " + ORDER_ID_COLUMN + " = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, orderId);
        ResultSet resultSet = statement.executeQuery();
        Set<OrderWorkflow> orderWorkflows = mapToObject(resultSet);
        return orderWorkflows;
    }

    private Set<OrderWorkflow> mapToObject(ResultSet resultSet) throws SQLException {
        Set<OrderWorkflow> orderWorkflows = new HashSet<>();
        while (resultSet.next()) {
            long orderId = resultSet.getLong(ORDER_ID_COLUMN);
            String state = resultSet.getString(ORDER_STATE_COLUMN);
            Date effectiveTime = new Date(resultSet.getTimestamp(EFFECTIVE_TIME_COLUMN).getTime());
            orderWorkflows.add(new OrderWorkflow(orderId, OrderState.valueOf(state), effectiveTime));
        }
        return orderWorkflows;
    }

}
