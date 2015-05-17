package com.quifers.dao;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

public class OrderDao {

    private final Connection connection;
    private final OrderWorkflowDao orderWorkflowDao;

    private final String TABLE_NAME = "orders";
    private final String ORDER_ID_COLUMN = "order_id";
    private final String NAME_COLUMN = "name";
    private final String MOBILE_NUMBER_COLUMN = "mobile_number";
    private final String EMAIL_COLUMN = "email";
    private final String FROM_ADDRESS_COLUMN = "from_address";
    private final String TO_ADDRESS_COLUMN = "to_address";
    private final String FIELD_EXECUTIVE_COLUMN = "field_executive_id";

    private String allColumns = ORDER_ID_COLUMN + "," + NAME_COLUMN + "," + MOBILE_NUMBER_COLUMN + "," +
            EMAIL_COLUMN + "," + FROM_ADDRESS_COLUMN + "," + TO_ADDRESS_COLUMN + "," + FIELD_EXECUTIVE_COLUMN;

    public OrderDao(Connection connection, OrderWorkflowDao orderWorkflowDao) {
        this.connection = connection;
        this.orderWorkflowDao = orderWorkflowDao;
    }

    public int saveOrder(Order order) throws SQLException {

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
        int orderRowsUpdated = statement.executeUpdate();
        orderWorkflowDao.saveOrderWorkflows(order.getOrderWorkflows());
        return orderRowsUpdated;
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
        Collection<OrderWorkflow> orderWorkflows = orderWorkflowDao.getOrderWorkflows(orderId);
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


}