package com.quifers.domain;

import com.quifers.db.annotations.DomainMapperFactory;
import com.quifers.db.annotations.Table;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "orders")
public class Order implements Serializable {

    private long orderId;

    private String name;

    private long mobileNumber;

    private String email;

    private String fromAddress;

    private String toAddress;

    private Date bookingDate;

    public Order(long orderId, String name, long mobileNumber, String email, String fromAddress, String toAddress, Date bookingDate) {
        this(name, mobileNumber, email, fromAddress, toAddress, bookingDate);
        this.orderId = orderId;
    }

    public Order(String name, long mobileNumber, String email, String fromAddress, String toAddress, Date bookingDate) {
        this.name = name;
        this.mobileNumber = mobileNumber;
        this.email = email;
        this.fromAddress = fromAddress;
        this.toAddress = toAddress;
        this.bookingDate = bookingDate;
    }

    public PreparedStatement getInsertStatement(Connection connection) throws SQLException {
        String sql = String.format("INSERT INTO " + DomainMapperFactory.getTableName(Order.class) + " (name,mobile_number,email,from_address,to_address,booking_date) " +
                "values (?,?,?,?,?,?)");
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, name);
        preparedStatement.setLong(2, mobileNumber);
        preparedStatement.setString(3, email);
        preparedStatement.setString(4, fromAddress);
        preparedStatement.setString(5, toAddress);
        preparedStatement.setTimestamp(6, new Timestamp(bookingDate.getTime()));
        return preparedStatement;
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId=" + orderId +
                ", name='" + name + '\'' +
                ", mobileNumber=" + mobileNumber +
                ", email='" + email + '\'' +
                ", fromAddress='" + fromAddress + '\'' +
                ", toAddress='" + toAddress + '\'' +
                ", bookingDate=" + bookingDate +
                '}';
    }
}
