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
    private final String VEHICLE_COLUMN = "vehicle";
    private final String FROM_ADDRESS_HOUSE_NUMBER_COLUMN = "from_address_house_number";
    private final String FROM_ADDRESS_SOCIETY_COLUMN = "from_address_society";
    private final String FROM_ADDRESS_AREA_COLUMN = "from_address_area";
    private final String FROM_ADDRESS_CITY_COLUMN = "from_address_city";
    private final String TO_ADDRESS_HOUSE_NUMBER_COLUMN = "to_address_house_number";
    private final String TO_ADDRESS_SOCIETY_COLUMN = "to_address_society";
    private final String TO_ADDRESS_AREA_COLUMN = "to_address_area";
    private final String TO_ADDRESS_CITY_COLUMN = "to_address_city";
    private final String LABOURS_COLUMN = "labours";
    private final String ESTIMATE_COLUMN = "estimate";
    private final String DISTANCE_COLUMN = "distance";
    private final String PICK_UP_FLOORS_COLUMN = "pick_up_floors";
    private final String PICK_UP_LIFT_WORKING_COLUMN = "pick_up_lift_working";
    private final String DROP_OFF_FLOORS_COLUMN = "drop_off_floors";
    private final String DROP_OFF_LIFT_WORKING_COLUMN = "drop_off_lift_working";
    private final String FIELD_EXECUTIVE_COLUMN = "field_executive_id";

    private final String ORDER_WORK_FLOWTABLE_NAME = "orders_workflow";
    private final String ORDER_STATE_COLUMN = "order_state";
    private final String EFFECTIVE_TIME_COLUMN = "effective_time";


    private String otherColumns = NAME_COLUMN + "," + MOBILE_NUMBER_COLUMN + "," +
            EMAIL_COLUMN + "," + VEHICLE_COLUMN + "," + FROM_ADDRESS_HOUSE_NUMBER_COLUMN + "," + FROM_ADDRESS_SOCIETY_COLUMN + ","
            + FROM_ADDRESS_AREA_COLUMN + "," + FROM_ADDRESS_CITY_COLUMN + "," + TO_ADDRESS_HOUSE_NUMBER_COLUMN + "," + TO_ADDRESS_SOCIETY_COLUMN + ","
            + TO_ADDRESS_AREA_COLUMN + "," + TO_ADDRESS_CITY_COLUMN + "," + LABOURS_COLUMN + "," + ESTIMATE_COLUMN + ","
            + DISTANCE_COLUMN + "," + PICK_UP_FLOORS_COLUMN + "," + PICK_UP_LIFT_WORKING_COLUMN + "," + DROP_OFF_FLOORS_COLUMN + ","
            + DROP_OFF_LIFT_WORKING_COLUMN  + "," + FIELD_EXECUTIVE_COLUMN;


    private String allColumns = ORDER_ID_COLUMN + "," + otherColumns;

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

    public void addOrderWorkflow(OrderWorkflow orderWorkflow) throws SQLException {
        saveOrderWorkflows(Arrays.asList(orderWorkflow));
    }

    public Order getOrder(long orderId) throws SQLException {
        String sql = "select orders." + ORDER_ID_COLUMN + " as order_id" + "," + otherColumns + ",order_state,effective_time" + " " +
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
        String sql = "select orders." + ORDER_ID_COLUMN + " as order_id" + "," + otherColumns + ",order_state,effective_time" + " " +
                "from orders inner join orders_workflow on orders.order_id = orders_workflow.order_id where orders.field_executive_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fieldExecutiveId);
        ResultSet resultSet = statement.executeQuery();
        return mapToObjects(resultSet);
    }

    private void saveOrderWithoutWorkflows(Order order) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" + allColumns + ")" + " " + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setLong(1, order.getOrderId());
        statement.setString(2, order.getName());
        statement.setLong(3, order.getMobileNumber());
        statement.setString(4, order.getEmail());
        statement.setString(5, order.getVehicle());
        statement.setString(6, order.getFromAddressHouseNumber());
        statement.setString(7, order.getFromAddressSociety());
        statement.setString(8, order.getFromAddressArea());
        statement.setString(9, order.getFromAddressCity());
        statement.setString(10, order.getToAddressHouseNumber());
        statement.setString(11, order.getToAddressSociety());
        statement.setString(12, order.getToAddressArea());
        statement.setString(13, order.getToAddressCity());
        statement.setInt(14, order.getLabours());
        statement.setString(15, order.getEstimate());
        statement.setString(16, order.getDistance());
        statement.setInt(17, order.getPickupFloors());
        statement.setBoolean(18, order.isPickupLiftWorking());
        statement.setInt(19, order.getDropOffFloors());
        statement.setBoolean(20, order.isDropOffLiftWorking());
        statement.setString(21, order.getFieldExecutiveId());
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
        order.setVehicle(resultSet.getString(VEHICLE_COLUMN));
        order.setFromAddressHouseNumber(resultSet.getString(FROM_ADDRESS_HOUSE_NUMBER_COLUMN));
        order.setFromAddressSociety(resultSet.getString(FROM_ADDRESS_SOCIETY_COLUMN));
        order.setFromAddressArea(resultSet.getString(FROM_ADDRESS_AREA_COLUMN));
        order.setFromAddressCity(resultSet.getString(FROM_ADDRESS_CITY_COLUMN));
        order.setToAddressHouseNumber(resultSet.getString(TO_ADDRESS_HOUSE_NUMBER_COLUMN));
        order.setToAddressSociety(resultSet.getString(TO_ADDRESS_SOCIETY_COLUMN));
        order.setToAddressArea(resultSet.getString(TO_ADDRESS_AREA_COLUMN));
        order.setToAddressCity(resultSet.getString(TO_ADDRESS_CITY_COLUMN));
        order.setLabours(resultSet.getInt(LABOURS_COLUMN));
        order.setEstimate(resultSet.getString(ESTIMATE_COLUMN));
        order.setDistance(resultSet.getString(DISTANCE_COLUMN));
        order.setPickupFloors(resultSet.getInt(PICK_UP_FLOORS_COLUMN));
        order.setPickupLiftWorking(resultSet.getBoolean(PICK_UP_LIFT_WORKING_COLUMN));
        order.setDropOffFloors(resultSet.getInt(DROP_OFF_FLOORS_COLUMN));
        order.setDropOffLiftWorking(resultSet.getBoolean(DROP_OFF_LIFT_WORKING_COLUMN));
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