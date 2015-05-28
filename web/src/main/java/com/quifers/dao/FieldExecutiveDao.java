package com.quifers.dao;

import com.quifers.domain.FieldExecutive;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class FieldExecutiveDao {

    private final Connection connection;

    private final String TABLE_NAME = "field_executive";
    private final String USER_ID_COLUMN = "user_id";
    private final String NAME_COLUMN = "name";
    private final String MOBILE_NUMBER_COLUMN = "mobile_number";

    public FieldExecutiveDao(Connection connection) {
        this.connection = connection;
    }

    public int saveFieldExecutive(FieldExecutive fieldExecutive) throws SQLException {
        String sql = "INSERT INTO " + TABLE_NAME + " " +
                "(" +
                USER_ID_COLUMN + "," +
                NAME_COLUMN + "," +
                MOBILE_NUMBER_COLUMN +
                ")" + " " +
                "VALUES(?,?,?)";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, fieldExecutive.getUserId());
        statement.setString(2, fieldExecutive.getName());
        statement.setLong(3, fieldExecutive.getMobileNumber());
        return statement.executeUpdate();
    }

    public FieldExecutive getFieldExecutive(String userId) throws SQLException {
        String sql = "SELECT" + " " +

                USER_ID_COLUMN + "," +
                NAME_COLUMN + "," +
                MOBILE_NUMBER_COLUMN + " " +

                "FROM" + " " + TABLE_NAME + " " +
                "WHERE user_id = ?";

        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, userId);
        ResultSet resultSet = statement.executeQuery();
        List<FieldExecutive> fieldExecutives = mapToObject(resultSet);
        return fieldExecutives.isEmpty() ? null : fieldExecutives.iterator().next();
    }

    public List<FieldExecutive> getAllFieldExecutives() throws SQLException {
        String sql = "SELECT" + " " +

                USER_ID_COLUMN + "," +
                NAME_COLUMN + "," +
                MOBILE_NUMBER_COLUMN + " " +

                "FROM" + " " + TABLE_NAME;

        PreparedStatement statement = connection.prepareStatement(sql);
        return mapToObject(statement.executeQuery());
    }

    private List<FieldExecutive> mapToObject(ResultSet resultSet) throws SQLException {
        List<FieldExecutive> fieldExecutives = new ArrayList<>();
        while (resultSet.next()) {
            String userId = resultSet.getString(USER_ID_COLUMN);
            String name = resultSet.getString(NAME_COLUMN);
            long mobileNumber = resultSet.getLong(MOBILE_NUMBER_COLUMN);
            fieldExecutives.add(new FieldExecutive(userId, name, mobileNumber));
        }
        return fieldExecutives;
    }


}