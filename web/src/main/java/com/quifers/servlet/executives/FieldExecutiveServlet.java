package com.quifers.servlet.executives;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.domain.Distance;
import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.EmailType;
import com.quifers.domain.enums.OrderState;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.FieldExecutiveGetAllOrdersRequest;
import com.quifers.request.FilterRequest;
import com.quifers.request.GeneratePriceRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.response.GeneratePriceResponse;
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

import static com.quifers.response.Responses.getOrderResponse;
import static com.quifers.servlet.listener.StartupContextListener.*;

public class FieldExecutiveServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveServlet.class);
    private OrderDao orderDao;
    private FieldExecutiveDao fieldExecutiveDao;
    private WebPublisher webPublisher;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
        fieldExecutiveDao = (FieldExecutiveDao) getServletContext().getAttribute(FIELD_EXECUTIVE_DAO);
        webPublisher = (WebPublisher) getServletContext().getAttribute(WEB_PUBLISHER);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestUri = request.getRequestURI();
            if ("/api/v0/executive/order/create/price".equals(requestUri)) {
                GeneratePriceRequest priceRequest = new GeneratePriceRequest(request);
                Order order = orderDao.getOrder(priceRequest.getOrderId());
                Distance distance = order.getDistance();
                distance.setDistance(priceRequest.getDistance());
                order.setDistance(distance);
                order.setWaitingMinutes(priceRequest.getWaitingMinutes());
                order.addOrderWorkflow(new OrderWorkflow(distance.getOrderId(), OrderState.COMPLETED, new Date()));
                orderDao.updateOrder(order);
                webPublisher.publishEmailMessage(EmailType.BILL_DETAILS, distance.getOrderId());
                GeneratePriceResponse priceResponse = new GeneratePriceResponse(response);
                priceResponse.writeResponse(order.getCost());
            } else if ("/api/v0/executive/order/get/all".equals(requestUri)) {
                FilterRequest filterRequest = new FilterRequest(request);
                FieldExecutiveGetAllOrdersRequest getAllOrdersRequest = new FieldExecutiveGetAllOrdersRequest(request);
                FieldExecutive fieldExecutive = fieldExecutiveDao.getFieldExecutive(new FieldExecutiveId(filterRequest.getUserId()));
                Collection<Order> orders = orderDao.getBookedOrders(fieldExecutive, getAllOrdersRequest.getBookingDate());
                response.setContentType("application/json");
                response.getWriter().write(getOrderResponse(orders));
            } else {
                response.sendError(HttpServletResponse.SC_NOT_FOUND);
            }
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Some error occurred.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }

    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }
}
