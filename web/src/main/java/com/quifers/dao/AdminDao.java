package com.quifers.dao;

import com.quifers.domain.Admin;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDao {

    private final Connection connection;

    private final String TABLE_NAME = "admin";
    private final String USER_ID_COLUMN = "user_id";
    private final String NAME_COLUMN = "name";
    private final String MOBILE_NUMBER_COLUMN = "mobile_number";

    public AdminDao(Connection connection) {
        this.connection = connection;
    }

    public int saveAdmin(Admin admin) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" +
                USER_ID_COLUMN + "," +
                NAME_COLUMN + "," +
                MOBILE_NUMBER_COLUMN +
                ")" + " " +
                "VALUES(?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, admin.getUserId());
        statement.setString(2, admin.getName());
        statement.setLong(3, admin.getMobileNumber());
        return statement.executeUpdate();
    }

    public Admin getAdmin(String userId) throws SQLException {
        String sql = "SELECT" + " " +

                USER_ID_COLUMN + "," +
                NAME_COLUMN + "," +
                MOBILE_NUMBER_COLUMN + " " +

                "FROM" + " " + TABLE_NAME + " " +
                "WHERE user_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();
        Admin admin = mapToObject(resultSet);
        return admin;
    }

    private Admin mapToObject(ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String userId = resultSet.getString(USER_ID_COLUMN);
            String name = resultSet.getString(NAME_COLUMN);
            long mobileNumber = resultSet.getLong(MOBILE_NUMBER_COLUMN);
            return new Admin(userId, name, mobileNumber);
        }
        return null;
    }


}