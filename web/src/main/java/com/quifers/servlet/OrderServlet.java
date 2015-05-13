package com.quifers.servlet;

import com.quifers.db.DatabaseHelper;
import com.quifers.domain.Order;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.quifers.listener.StartupContextListener.DATABASE_HELPER;

public class OrderServlet extends HttpServlet {

    private DatabaseHelper databaseHelper;

    @Override
    public void init() throws ServletException {
        databaseHelper = (DatabaseHelper) getServletContext().getAttribute(DATABASE_HELPER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String clientName = request.getParameter("client_name");
            long mobileNumber = Long.valueOf(request.getParameter("mobile_number"));
            String email = request.getParameter("email");
            String fromAddress = request.getParameter("from_address");
            String toAddress = request.getParameter("to_address");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date bookingDate = dateFormat.parse(request.getParameter("booking_date"));
            Order order = new Order(clientName, mobileNumber, email, fromAddress, toAddress, bookingDate);
            databaseHelper.saveOrder(order);
            List<Order> orders = databaseHelper.getOrdersByName(clientName);
            response.getWriter().write(orders.iterator().next().toString());
        } catch (SQLException e) {
            response.getWriter().write("Unable to save client.");
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
