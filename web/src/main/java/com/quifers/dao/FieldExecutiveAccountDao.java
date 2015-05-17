package com.quifers.dao;

import com.quifers.domain.FieldExecutiveAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class FieldExecutiveAccountDao {

    private final Connection connection;

    private final String TABLE_NAME = "field_executive_account";
    private final String USER_ID_COLUMN = "user_id";
    private final String PASSWORD_COLUMN = "password";

    public FieldExecutiveAccountDao(Connection connection) {
        this.connection = connection;
    }

    public int saveAccount(FieldExecutiveAccount fieldExecutiveAccount) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" +
                USER_ID_COLUMN + "," +
                PASSWORD_COLUMN +
                ")" + " " +
                "VALUES(?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fieldExecutiveAccount.getUserId());
        statement.setString(2, fieldExecutiveAccount.getPassword());
        return statement.executeUpdate();
    }

    public FieldExecutiveAccount getAccount(String userId) throws SQLException {
        String sql = "SELECT" + " " +
                USER_ID_COLUMN + "," + PASSWORD_COLUMN + " " +
                "FROM" + " " + TABLE_NAME + " " +
                "WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();
        FieldExecutiveAccount fieldExecutiveAccount = mapToObject(resultSet);
        return fieldExecutiveAccount;
    }

    private FieldExecutiveAccount mapToObject(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String userId = resultSet.getString(USER_ID_COLUMN);
            String password = resultSet.getString(PASSWORD_COLUMN);
            return new FieldExecutiveAccount(userId, password);
        }
        return null;
    }


}