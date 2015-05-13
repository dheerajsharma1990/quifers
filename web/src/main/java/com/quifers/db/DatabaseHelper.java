package com.quifers.db;

import com.quifers.domain.Client;

import java.sql.*;

public class DatabaseHelper {
    private final Connection connection;

    public DatabaseHelper(String url) throws SQLException {
        this.connection = DriverManager.getConnection(url);;
    }

    public int saveClient(Client client) throws SQLException {
        PreparedStatement statement = client.getInsertStatement(connection);
        return statement.executeUpdate();
    }

    public Client getClientByMobile(long mobileNumber) throws SQLException {
        String sql = String.format("SELECT %s,%s,%s,%s FROM %s WHERE %s = ?", "client_id", "name", "mobile_number", "email", "client", "mobile_number");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setLong(1, mobileNumber);
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            long clientId = resultSet.getLong("client_id");
            String name = resultSet.getString("name");
            long mobile = resultSet.getLong("mobile_number");
            String email = resultSet.getString("email");
            return new Client(clientId, name, mobileNumber, email);
        }
        return null;
    }
}
