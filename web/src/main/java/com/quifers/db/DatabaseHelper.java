package com.quifers.db;

import com.quifers.domain.QuifersDomainObject;

import java.sql.*;
import java.util.List;

import static com.quifers.db.DomainMapperFactory.createSelectStatement;
import static com.quifers.db.DomainMapperFactory.setInsertParameters;

public class DatabaseHelper {
    private final Connection connection;

    public DatabaseHelper(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);
    }

    public int save(QuifersDomainObject order) throws SQLException, IllegalAccessException {
        String sql = DomainMapperFactory.getInsertSql(order.getClass());
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        setInsertParameters(preparedStatement, order);
        return preparedStatement.executeUpdate();
    }

    public <T extends QuifersDomainObject> List<T> getObjects(Class<T> clazz, String columnName, Object columnValue) throws NoSuchFieldException, SQLException, InstantiationException, IllegalAccessException {
        DbColumn dbColumn = new DbColumn(clazz.getDeclaredField(columnName));
        String sql = DomainMapperFactory.getCreateSql(clazz, dbColumn);
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        createSelectStatement(preparedStatement, dbColumn, columnValue);
        ResultSet resultSet = preparedStatement.executeQuery();
        return DomainMapperFactory.mapObjects(resultSet, clazz);
    }

}
