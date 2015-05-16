package com.quifers.servlet;

import com.quifers.db.DatabaseHelper;
import com.quifers.domain.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static com.quifers.listener.StartupContextListener.DATABASE_HELPER;

public class AssignOrderToFieldManagerServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AssignOrderToFieldManagerServlet.class);

    private DatabaseHelper databaseHelper;


    @Override
    public void init() throws ServletException {
        databaseHelper = (DatabaseHelper) getServletContext().getAttribute(DATABASE_HELPER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            long orderId = Long.valueOf(request.getParameter("order_id"));
            String fieldManagerId = request.getParameter("field_manager_id");
            databaseHelper.updateOrder(orderId, fieldManagerId);
            List<Order> orders = databaseHelper.getObjects(Order.class, "orderId", orderId);
            LOGGER.info("Order: {}", orders.iterator().next());
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
