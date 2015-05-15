package com.quifers.db;

import com.quifers.domain.Order;

import java.sql.*;
import java.util.List;

import static com.quifers.db.DomainMapperFactory.createSelectStatement;
import static com.quifers.db.DomainMapperFactory.setInsertParameters;

public class DatabaseHelper {
    private final Connection connection;

    public DatabaseHelper(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public int saveOrder(Order order) throws SQLException, IllegalAccessException {
        String sql = DomainMapperFactory.getInsertSql(order.getClass());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setInsertParameters(preparedStatement, order);
        return preparedStatement.executeUpdate();
    }

    public List<Order> getOrdersByName(String name) throws SQLException, InstantiationException, IllegalAccessException, NoSuchFieldException {
        DbColumn dbColumn = new DbColumn("name", Order.class.getDeclaredField("name"));
        String sql = DomainMapperFactory.getCreateSql(Order.class, dbColumn);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        createSelectStatement(preparedStatement, dbColumn, name);
        ResultSet resultSet = preparedStatement.executeQuery();
        List<Order> orders = DomainMapperFactory.mapObjects(resultSet, Order.class);
        return orders;
    }

}
