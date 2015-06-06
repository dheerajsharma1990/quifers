package com.quifers.servlet.executives;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.request.OrderByOrderIdRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.response.FieldExecutiveResponse;
import com.quifers.servlet.OrderServlet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.servlet.listener.StartupContextListener.ORDER_DAO;

public class OrderByOrderIdServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServlet.class);

    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
    }


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            OrderByOrderIdRequest orderByOrderIdRequest = new OrderByOrderIdRequest(request);
            Order order = orderDao.getOrder(orderByOrderIdRequest.getOrderId());
            response.setContentType("application/json");
            response.getWriter().write(FieldExecutiveResponse.getOrderResponse(order));
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error in saving order.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to send order.");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
