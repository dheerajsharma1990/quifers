package com.quifers.dao;

import com.quifers.domain.Admin;
import com.quifers.domain.AdminAccount;

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
    private final AdminAccountDao adminAccountDao;

    public AdminDao(Connection connection) {
        this.connection = connection;
        this.adminAccountDao = new AdminAccountDao(connection);
    }

    public int saveAdmin(Admin admin) throws SQLException {

        adminAccountDao.saveAccount(admin.getAccount());

        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" +
                USER_ID_COLUMN + "," +
                NAME_COLUMN + "," +
                MOBILE_NUMBER_COLUMN +
                ")" + " " +
                "VALUES(?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, admin.getAccount().getUserId());
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
        AdminAccount account = adminAccountDao.getAccount(userId);
        Admin admin = mapToObject(account,resultSet);
        return admin;
    }

    private Admin mapToObject(AdminAccount account, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            String name = resultSet.getString(NAME_COLUMN);
            long mobileNumber = resultSet.getLong(MOBILE_NUMBER_COLUMN);
            return new Admin(account, name, mobileNumber);
        }
        return null;
    }


}