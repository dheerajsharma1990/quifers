package com.quifers.domain;

import com.quifers.db.DomainMapperFactory;
import com.quifers.db.annotations.Column;
import com.quifers.db.annotations.Table;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;

@Table(name = "orders")
public class Order implements QuifersDomainObject {

    @Column(name = "order_id")
    private long orderId;

    @Column(name = "name")
    private String name;

    @Column(name = "mobile_number")
    private long mobileNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "from_address")
    private String fromAddress;

    @Column(name = "to_address")
    private String toAddress;

    @Column(name = "booking_date")
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

    public String getName() {
        return name;
    }

    public long getMobileNumber() {
        return mobileNumber;
    }

    public String getEmail() {
        return email;
    }

    public String getFromAddress() {
        return fromAddress;
    }

    public String getToAddress() {
        return toAddress;
    }

    public Date getBookingDate() {
        return bookingDate;
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
