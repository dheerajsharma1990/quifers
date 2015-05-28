package com.quifers.dao;

import com.quifers.domain.AdminAccount;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminAccountDao {

    private final Connection connection;

    private final String TABLE_NAME = "admin_account";
    private final String USER_ID_COLUMN = "user_id";
    private final String PASSWORD_COLUMN = "password";

    public AdminAccountDao(Connection connection) {
        this.connection = connection;
    }

    public int saveAccount(AdminAccount adminAccount) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" +
                USER_ID_COLUMN + "," +
                PASSWORD_COLUMN +
                ")" + " " +
                "VALUES(?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, adminAccount.getUserId());
        statement.setString(2, adminAccount.getPassword());
        return statement.executeUpdate();
    }

    public AdminAccount getAccount(String userId) throws SQLException {
        String sql = "SELECT" + " " +
                USER_ID_COLUMN + "," + PASSWORD_COLUMN + " " +
                "FROM" + " " + TABLE_NAME + " " +
                "WHERE user_id = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();
        AdminAccount adminAccount = mapToObject(resultSet);
        return adminAccount;
    }

    private AdminAccount mapToObject(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String userId = resultSet.getString(USER_ID_COLUMN);
            String password = resultSet.getString(PASSWORD_COLUMN);
            return new AdminAccount(userId, password);
        }
        return null;
    }


}