package com.quifers.servlet.executives;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.enums.OrderState;
import com.quifers.request.ChangeOrderRequest;
import com.quifers.request.GeneratePriceRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.response.FieldExecutiveResponse;
import com.quifers.servlet.listener.WebPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Date;

import static com.quifers.response.FieldExecutiveResponse.getSuccessResponse;
import static com.quifers.response.PriceResponse.getPriceResponse;
import static com.quifers.servlet.listener.StartupContextListener.ORDER_DAO;
import static com.quifers.servlet.listener.StartupContextListener.WEB_PUBLISHER;

public class FieldExecutiveServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveServlet.class);
    private OrderDao orderDao;
    private WebPublisher webPublisher;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
        webPublisher = (WebPublisher) getServletContext().getAttribute(WEB_PUBLISHER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestUri = request.getRequestURI();
            if ("/api/v0/executive/order/update/state".equals(requestUri)) {
                ChangeOrderRequest changeOrderRequest = new ChangeOrderRequest(request);
                orderDao.addOrderWorkflow(new OrderWorkflow(changeOrderRequest.getOrderId(), OrderState.valueOf(changeOrderRequest.getOrderState().toUpperCase()), new Date()));
                response.setContentType("application/json");
                response.getWriter().write(getSuccessResponse());
            } else if ("/api/v0/executive/order/create/price".equals(requestUri)) {
                GeneratePriceRequest priceRequest = new GeneratePriceRequest(request);
                Order order = orderDao.getOrder(priceRequest.getOrderId());
                order.setDistance(priceRequest.getDistance());
                order.addOrderWorkflow(new OrderWorkflow(order.getOrderId(), OrderState.COMPLETED, new Date()));
                orderDao.updateOrder(order);
                webPublisher.publishEmailMessage(EmailType.BILL_DETAILS, order.getOrderId());
                response.setContentType("application/json");
                response.getWriter().write(getPriceResponse(order.getCost()));
            } else if ("/api/v0/executive/order/get/all".equals(requestUri)) {
                Collection<Order> orders = orderDao.getAllOrders();
                response.setContentType("application/json");
                response.getWriter().write(FieldExecutiveResponse.getOrderResponse(orders));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
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
