package com.quifers.servlet.admin;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.response.FieldExecutiveResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.quifers.servlet.listener.StartupContextListener.ORDER_DAO;

public class AllOrdersServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AllOrdersServlet.class);

    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Collection<Order> orders = orderDao.getAllOrders();
            response.setContentType("application/json");
            response.getWriter().write(FieldExecutiveResponse.getOrderResponse(orders));
        }  catch (Exception e) {
            LOGGER.error("Error in saving order.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to send order.");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
