package com.quifers.db;

import com.quifers.domain.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.quifers.db.DomainMapperFactory.setParameters;

public class DatabaseHelper {
    private final Connection connection;

    public DatabaseHelper(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public int saveOrder(Order order) throws SQLException, IllegalAccessException {
        String sql = DomainMapperFactory.getInsertSql(order.getClass());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setParameters(preparedStatement, order);
        return preparedStatement.executeUpdate();
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
