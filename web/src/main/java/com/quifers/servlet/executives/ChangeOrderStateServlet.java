package com.quifers.servlet.executives;

import com.quifers.dao.OrderDao;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.request.ChangeOrderRequest;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static com.quifers.response.FieldExecutiveResponse.getSuccessResponse;
import static com.quifers.servlet.listener.StartupContextListener.ORDER_DAO;

public class ChangeOrderStateServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChangeOrderStateServlet.class);
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ChangeOrderRequest changeOrderRequest = new ChangeOrderRequest(request);
            orderDao.addOrderWorkflow(new OrderWorkflow(changeOrderRequest.getOrderId(), OrderState.valueOf(changeOrderRequest.getOrderState().toUpperCase()), new Date()));
            response.setContentType("application/json");
            response.getWriter().write(getSuccessResponse());
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error occurred in registering field executive account.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
