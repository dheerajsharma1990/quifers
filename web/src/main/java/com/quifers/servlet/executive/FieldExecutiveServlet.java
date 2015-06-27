package com.quifers.servlet.executive;

import com.quifers.domain.FieldExecutive;
import com.quifers.domain.Order;
import com.quifers.domain.id.FieldExecutiveId;
import com.quifers.request.FieldExecutiveGetAllOrdersRequest;
import com.quifers.request.FilterRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.BaseServlet;
import com.quifers.servlet.RequestHandler;
import com.quifers.servlet.executive.request.ReceivableRequest;
import com.quifers.servlet.executive.request.ReceivableRequestValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.quifers.response.Responses.getOrderResponse;
import static com.quifers.response.Responses.getReceivableResponse;

public class FieldExecutiveServlet extends BaseServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestUri = request.getRequestURI();
            if ("/api/v0/executive/order/create/price".equals(requestUri)) {
                RequestHandler requestHandler = fieldExecutiveRequestHandlerFactory.getRequestHandler(request);
                requestHandler.handleRequest(request, response);
            } else if ("/api/v0/executive/order/get/all".equals(requestUri)) {
                FilterRequest filterRequest = new FilterRequest(request);
                FieldExecutiveGetAllOrdersRequest getAllOrdersRequest = new FieldExecutiveGetAllOrdersRequest(request);
                FieldExecutive fieldExecutive = fieldExecutiveDao.getFieldExecutive(new FieldExecutiveId(filterRequest.getUserId()));
                Collection<Order> orders = orderDao.getBookedOrders(fieldExecutive, getAllOrdersRequest.getBookingDate());
                response.setContentType("application/json");
                response.getWriter().write(getOrderResponse(orders));
            } else if ("/api/v0/executive/order/receivables".equals(requestUri)) {
                ReceivableRequest receivableRequest = new ReceivableRequestValidator().validateRequest(request);
                Order order = orderDao.getOrder(receivableRequest.getOrderId());
                order.setReceivables(receivableRequest.getReceivables());
                orderDao.updateOrder(order);
                response.setContentType("application/json");
                response.getWriter().write(getReceivableResponse(order));
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
