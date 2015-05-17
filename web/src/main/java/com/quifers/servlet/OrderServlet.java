package com.quifers.servlet;

import com.quifers.dao.OrderDao;
import com.quifers.dao.OrderWorkflowDao;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;

import static com.quifers.listener.StartupContextListener.ORDER_DAO;
import static com.quifers.listener.StartupContextListener.ORDER_ID_COUNTER;
import static com.quifers.listener.StartupContextListener.ORDER_WORKFLOW_DAO;

public class OrderServlet extends HttpServlet {

    private OrderDao orderDao;
    private OrderWorkflowDao orderWorkflowDao;
    private AtomicLong orderIdCounter;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
        orderWorkflowDao = (OrderWorkflowDao) getServletContext().getAttribute(ORDER_WORKFLOW_DAO);
        orderIdCounter = (AtomicLong) getServletContext().getAttribute(ORDER_ID_COUNTER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long orderId = orderIdCounter.getAndIncrement();
            String clientName = request.getParameter("client_name");
            long mobileNumber = Long.valueOf(request.getParameter("mobile_number"));
            String email = request.getParameter("email");
            String fromAddress = request.getParameter("from_address");
            String toAddress = request.getParameter("to_address");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
            Date bookingDate = dateFormat.parse(request.getParameter("booking_date"));
            OrderWorkflow orderWorkflow = new OrderWorkflow(orderId, OrderState.BOOKED, bookingDate);
            Order order = new Order(orderId, clientName, mobileNumber, email, fromAddress, toAddress, null, new HashSet<>(Arrays.asList(orderWorkflow)));
            orderDao.saveOrder(order);
        } catch (SQLException e) {
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
