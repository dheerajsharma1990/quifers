package com.quifers.servlet;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.request.validators.OrderBookRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.quifers.listener.StartupContextListener.ORDER_BOOK_REQUEST_VALIDATOR;
import static com.quifers.listener.StartupContextListener.ORDER_DAO;

public class OrderServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServlet.class);

    private OrderDao orderDao;
    private OrderBookRequestValidator requestValidator;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
        requestValidator = (OrderBookRequestValidator) getServletContext().getAttribute(ORDER_BOOK_REQUEST_VALIDATOR);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Order order = requestValidator.validateRequest(request);
            orderDao.saveOrder(order);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
