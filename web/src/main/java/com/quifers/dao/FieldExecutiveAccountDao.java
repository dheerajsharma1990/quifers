package com.quifers.dao;

import com.quifers.domain.FieldExecutiveAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;

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
        Set<FieldExecutiveAccount> fieldExecutiveAccounts = mapToObjects(resultSet);
        return fieldExecutiveAccounts.size() != 0 ? fieldExecutiveAccounts.iterator().next() : null;
    }

    public Set<FieldExecutiveAccount> getAllAccounts() throws SQLException {
        String sql = "SELECT" + " " +
                USER_ID_COLUMN + "," + PASSWORD_COLUMN + " " +
                "FROM" + " " + TABLE_NAME;
        PreparedStatement statement = connection.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        return mapToObjects(resultSet);
    }

    private Set<FieldExecutiveAccount> mapToObjects(ResultSet resultSet) throws SQLException {
        Set<FieldExecutiveAccount> fieldExecutiveAccounts = new HashSet<>();
        while (resultSet.next()) {
            String userId = resultSet.getString(USER_ID_COLUMN);
            String password = resultSet.getString(PASSWORD_COLUMN);
            fieldExecutiveAccounts.add(new FieldExecutiveAccount(userId, password));
        }
        return fieldExecutiveAccounts;
    }


}