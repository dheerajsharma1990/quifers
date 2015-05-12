package com.quifers.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;

public class HelloServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            //Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/quifersdb?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8", "root", "");
            Connection connection = DriverManager.getConnection("jdbc:mysql://quifers-db-instance.co33vvbebuaf.ap-southeast-1.rds.amazonaws.com:3006/quifersdb?zeroDateTimeBehavior=convertToNull&useUnicode=yes&characterEncoding=UTF-8", "quifers", "quiferspassword");
            PreparedStatement statement = connection.prepareStatement("SELECT name,mobile from client");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                Long mobile = resultSet.getLong("mobile");
                response.getWriter().write("Name: " + name + ",Mobile: " + mobile);
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
