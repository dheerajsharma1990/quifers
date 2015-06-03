package com.quifers.servlet.executives;

import com.quifers.dao.OrderDao;
import com.quifers.domain.Order;
import com.quifers.domain.Price;
import com.quifers.request.GeneratePriceRequest;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.response.PriceResponse.getPriceResponse;
import static com.quifers.servlet.listener.StartupContextListener.ORDER_DAO;

public class GeneratePriceServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(GeneratePriceServlet.class);

    private OrderDao orderDao;


    @Override
    public void init() throws ServletException {
        this.orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            GeneratePriceRequest priceRequest = new GeneratePriceRequest(request);
            Order order = orderDao.getOrder(priceRequest.getOrderId());
            Price price = new Price(order.getOrderId(), order.getWaitingMinutes(), order.getDistance(), order.getLabours(), order.getNonWorkingPickUpFloors() + order.getNonWorkingDropOffFloors());
            response.setContentType("application/json");
            response.getWriter().write(getPriceResponse(price));
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
