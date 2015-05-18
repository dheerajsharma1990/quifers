package com.quifers.dao;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;

import java.sql.*;
import java.sql.Date;
import java.util.*;

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
        saveOrderWithoutWorkflows(order);
        saveOrderWorkflows(order.getOrderWorkflows());
        connection.commit();
        connection.setAutoCommit(true);
    }

    public Order getOrder(long orderId) throws SQLException {
        String sql = "select orders.order_id as order_id,name,mobile_number,email,from_address,to_address,field_executive_id,order_state,effective_time " +
                "from orders inner join orders_workflow on orders.order_id = orders_workflow.order_id where orders.order_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, orderId);
        ResultSet resultSet = statement.executeQuery();
        List<Order> orders = mapToObjects(resultSet);
        return orders.size() == 0 ? null : orders.iterator().next();
    }

    public int assignFieldExecutiveToOrder(long orderId, String fieldExecutiveId) throws SQLException {
        String sql = "UPDATE" + " " + TABLE_NAME + " SET " + FIELD_EXECUTIVE_COLUMN + " = ? " +
                "WHERE " + ORDER_ID_COLUMN + " = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fieldExecutiveId);
        statement.setLong(2, orderId);
        return statement.executeUpdate();
    }

    public List<Order> getOrderByFieldExecutiveId(String fieldExecutiveId) throws SQLException {
        String sql = "select orders.order_id as order_id,name,mobile_number,email,from_address,to_address,field_executive_id,order_state,effective_time " +
                "from orders inner join orders_workflow on orders.order_id = orders_workflow.order_id where orders.field_executive_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fieldExecutiveId);
        ResultSet resultSet = statement.executeQuery();
        return mapToObjects(resultSet);
    }

    private void saveOrderWithoutWorkflows(Order order) throws SQLException {
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

    private List<Order> mapToObjects(ResultSet resultSet) throws SQLException {
        Set<Order> orders = new HashSet<>();
        Set<OrderWorkflow> orderWorkflows = new HashSet<>();
        while (resultSet.next()) {
            Order order = new Order();
            OrderWorkflow orderWorkflow = new OrderWorkflow();
            long orderId = mapOrder(resultSet, orders, order);
            mapOrderWorkflow(resultSet, orderWorkflows, orderWorkflow, orderId);
        }
        return mapObject(orders, orderWorkflows);
    }

    private long mapOrder(ResultSet resultSet, Set<Order> orders, Order order) throws SQLException {
        long orderId = resultSet.getLong(ORDER_ID_COLUMN);
        order.setOrderId(orderId);
        order.setName(resultSet.getString(NAME_COLUMN));
        order.setMobileNumber(resultSet.getLong(MOBILE_NUMBER_COLUMN));
        order.setEmail(resultSet.getString(EMAIL_COLUMN));
        order.setFromAddress(resultSet.getString(FROM_ADDRESS_COLUMN));
        order.setToAddress(resultSet.getString(TO_ADDRESS_COLUMN));
        order.setFieldExecutiveId(resultSet.getString(FIELD_EXECUTIVE_COLUMN));
        orders.add(order);
        return orderId;
    }

    private void mapOrderWorkflow(ResultSet resultSet, Set<OrderWorkflow> orderWorkflows, OrderWorkflow orderWorkflow, long orderId) throws SQLException {
        orderWorkflow.setOrderId(orderId);
        orderWorkflow.setOrderState(OrderState.valueOf(resultSet.getString(ORDER_STATE_COLUMN)));
        orderWorkflow.setEffectiveTime(new Date(resultSet.getTimestamp(EFFECTIVE_TIME_COLUMN).getTime()));
        orderWorkflows.add(orderWorkflow);
    }

    private List<Order> mapObject(Set<Order> orders, Set<OrderWorkflow> orderWorkflows) {
        List<Order> orderList = new ArrayList<>();
        for (Order order : orders) {
            for (OrderWorkflow workflow : orderWorkflows) {
                if (workflow.getOrderId() == order.getOrderId()) {
                    order.addOrderWorkflow(workflow);
                }
            }
            orderList.add(order);
        }
        return orderList;
    }

}