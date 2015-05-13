package com.quifers.domain;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Client implements Serializable {

    private long clientId;

    private String name;

    private long mobileNumber;

    private String email;

    public Client(long clientId, String name, long mobileNumber, String email) {
        this.clientId = clientId;
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public Client(String name, long mobileNumber, String email) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
    }


    public long getClientId() {
        return clientId;
    }

    public String getName() {
        return name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }


    public PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        String sql = String.format("INSERT INTO %s (%s,%s,%s) VALUES(?,?,?)", "client", "name", "mobile_number", "email");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setLong(2, mobileNumber);
        preparedStatement.setString(3, email);
        return preparedStatement;
    }

}
