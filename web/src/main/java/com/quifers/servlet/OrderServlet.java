package com.quifers.servlet;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.enums.EmailType;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.request.validators.OrderBookRequestValidator;
import com.quifers.servlet.listener.WebPublisher;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.servlet.listener.StartupContextListener.*;

public class OrderServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServlet.class);

    private OrderDao orderDao;
    private OrderBookRequestValidator requestValidator;
    private WebPublisher webPublisher;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
        requestValidator = (OrderBookRequestValidator) getServletContext().getAttribute(ORDER_BOOK_REQUEST_VALIDATOR);
        webPublisher = (WebPublisher) getServletContext().getAttribute(WEB_PUBLISHER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Order order = requestValidator.validateRequest(request);
            orderDao.saveOrder(order);
            webPublisher.publishEmailMessage(EmailType.NEW_ORDER, order.getOrderId());
            JSONObject object = new JSONObject();
            object.put("success", "true");
            response.setContentType("application/json");
            response.getWriter().write(object.toString());
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (JMSException e) {
            LOGGER.error("Error in sending email message.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to send email.");
        } catch (Exception e) {
            LOGGER.error("Error in saving order.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Unable to book order.");
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
