package com.quifers.servlet.admin;

import com.quifers.dao.OrderDao;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

import static com.quifers.listener.StartupContextListener.ORDER_DAO;

public class FieldExecutiveAssignServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveAssignServlet.class);

    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String fieldExecutiveId = request.getParameter("fieldExecutiveId");
            long orderId = Long.valueOf(request.getParameter("orderId"));
            orderDao.assignFieldExecutiveToOrder(orderId, fieldExecutiveId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("success", true);
            response.setContentType("application/json");
            response.getWriter().write(jsonObject.toString());
        } catch (SQLException e) {
            LOGGER.error("Error occurred in field executives.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }

}
