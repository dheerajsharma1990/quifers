package com.quifers.db;

import com.quifers.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHelper {
    private final Connection connection;

    public DatabaseHelper(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public int saveOrder(Order order) throws SQLException {
        String sql = DomainMapperFactory.getInsertSql(order.getClass());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, order.getName());
        preparedStatement.setLong(2, order.getMobileNumber());
        preparedStatement.setString(3, order.getEmail());
        preparedStatement.setString(4, order.getFromAddress());
        preparedStatement.setString(5, order.getToAddress());
        preparedStatement.setTimestamp(6, new Timestamp(order.getBookingDate().getTime()));
        PreparedStatement statement = order.getInsertStatement(connection);
        return statement.executeUpdate();
    }

    public List<Order> getOrdersByName(String name) throws SQLException {
        String sql = "SELECT order_id,name,mobile_number,email,from_address,to_address,booking_date from " + DomainMapperFactory.getTableName(Order.class) + " where name = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> orders = new ArrayList<>();
        while (resultSet.next()) {
            long id = resultSet.getLong("order_id");
            String clientName = resultSet.getString("name");
            long mobileNumber = resultSet.getLong("mobile_number");
            String email = resultSet.getString("email");
            String fromAddress = resultSet.getString("from_address");
            String toAddress = resultSet.getString("to_address");
            Date bookingDate = new Date(resultSet.getTimestamp("booking_date").getTime());
            orders.add(new Order(id, clientName, mobileNumber, email, fromAddress, toAddress, bookingDate));
        }
        return orders;
    }

}
