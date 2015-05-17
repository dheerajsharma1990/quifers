package com.quifers.dao;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class OrderDao {

    private final Connection connection;

    private final String TABLE_NAME = "orders";
    private final String ORDER_ID_COLUMN = "order_id";
    private final String NAME_COLUMN = "name";
    private final String MOBILE_NUMBER_COLUMN = "mobile_number";
    private final String EMAIL_COLUMN = "email";
    private final String FROM_ADDRESS_COLUMN = "from_address";
    private final String TO_ADDRESS_COLUMN = "to_address";
    private final String FIELD_EXECUTIVE_COLUMN = "field_executive_id";

    private final String ORDER_WORK_FLOWTABLE_NAME = "orders_workflow";
    private final String ORDER_STATE_COLUMN = "order_state";
    private final String EFFECTIVE_TIME_COLUMN = "effective_time";

    private String allColumns = ORDER_ID_COLUMN + "," + NAME_COLUMN + "," + MOBILE_NUMBER_COLUMN + "," +
            EMAIL_COLUMN + "," + FROM_ADDRESS_COLUMN + "," + TO_ADDRESS_COLUMN + "," + FIELD_EXECUTIVE_COLUMN;

    private String allWorkflowColumns = ORDER_ID_COLUMN + "," + ORDER_STATE_COLUMN + "," + EFFECTIVE_TIME_COLUMN;

    public OrderDao(Connection connection) {
        this.connection = connection;
    }

    public void saveOrder(Order order) throws SQLException {
        connection.setAutoCommit(false);
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" + allColumns + ")" + " " + "VALUES(?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, order.getOrderId());
        statement.setString(2, order.getName());
        statement.setLong(3, order.getMobileNumber());
        statement.setString(4, order.getEmail());
        statement.setString(5, order.getFromAddress());
        statement.setString(6, order.getToAddress());
        statement.setString(7, order.getFieldExecutiveId());
        statement.executeUpdate();
        saveOrderWorkflows(order.getOrderWorkflows());
        connection.commit();
        connection.setAutoCommit(true);
    }

    private void saveOrderWorkflows(Collection<OrderWorkflow> orderWorkflows) throws SQLException {
        String sql = "INSERT INTO " + ORDER_WORK_FLOWTABLE_NAME + " " +
                "(" + allWorkflowColumns + ")" + " " + "VALUES(?,?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        for (OrderWorkflow orderWorkflow : orderWorkflows) {
            preparedStatement.setLong(1, orderWorkflow.getOrderId());
            preparedStatement.setString(2, orderWorkflow.getOrderState().name());
            preparedStatement.setTimestamp(3, new Timestamp(orderWorkflow.getEffectiveTime().getTime()));
            preparedStatement.addBatch();
        }
        preparedStatement.executeBatch();
    }

    public int assignFieldExecutiveToOrder(long orderId, String fieldExecutiveId) throws SQLException {
        String sql = "UPDATE" + " " + TABLE_NAME + " SET " + FIELD_EXECUTIVE_COLUMN + " = ? " +
                "WHERE " + ORDER_ID_COLUMN + " = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fieldExecutiveId);
        statement.setLong(2, orderId);
        return statement.executeUpdate();
    }

    public Order getOrder(long orderId) throws SQLException {
        String sql = "SELECT" + " " + allColumns + " FROM " + TABLE_NAME + " WHERE " + ORDER_ID_COLUMN + " = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, orderId);
        ResultSet resultSet = statement.executeQuery();
        Collection<OrderWorkflow> orderWorkflows = getOrderWorkflows(orderId);
        Order order = mapToObject(resultSet, orderWorkflows);
        return order;
    }

    private Order mapToObject(ResultSet resultSet, Collection<OrderWorkflow> orderWorkflows) throws SQLException {
        while (resultSet.next()) {
            long orderId = resultSet.getLong(ORDER_ID_COLUMN);
            String name = resultSet.getString(NAME_COLUMN);
            long mobileNumber = resultSet.getLong(MOBILE_NUMBER_COLUMN);
            String email = resultSet.getString(EMAIL_COLUMN);
            String fromAddress = resultSet.getString(FROM_ADDRESS_COLUMN);
            String toAddress = resultSet.getString(TO_ADDRESS_COLUMN);
            String fieldExecutiveId = resultSet.getString(FIELD_EXECUTIVE_COLUMN);
            return new Order(orderId, name, mobileNumber, email, fromAddress, toAddress, fieldExecutiveId, orderWorkflows);
        }
        return null;
    }

    public Collection<OrderWorkflow> getOrderWorkflows(long orderId) throws SQLException {
        String sql = "SELECT" + " " + allWorkflowColumns + " FROM " + ORDER_WORK_FLOWTABLE_NAME + " WHERE " + ORDER_ID_COLUMN + " = ?";
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
            java.util.Date effectiveTime = new java.util.Date(resultSet.getTimestamp(EFFECTIVE_TIME_COLUMN).getTime());
            orderWorkflows.add(new OrderWorkflow(orderId, OrderState.valueOf(state), effectiveTime));
        }
        return orderWorkflows;
    }


}