package com.quifers.servlet.admin;

import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.OrderDao;
import com.quifers.domain.FieldExecutive;
import com.quifers.request.FieldExecutiveAssignRequest;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.response.FieldExecutiveResponse.getSuccessResponse;
import static com.quifers.servlet.listener.StartupContextListener.FIELD_EXECUTIVE_DAO;
import static com.quifers.servlet.listener.StartupContextListener.ORDER_DAO;
import static java.lang.Long.valueOf;

public class FieldExecutiveAssignServlet extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveAssignServlet.class);

    private FieldExecutiveDao fieldExecutiveDao;
    private OrderDao orderDao;

    @Override
    public void init() throws ServletException {
        fieldExecutiveDao = (FieldExecutiveDao) getServletContext().getAttribute(FIELD_EXECUTIVE_DAO);
        orderDao = (OrderDao) getServletContext().getAttribute(ORDER_DAO);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            FieldExecutiveAssignRequest assignRequest = new FieldExecutiveAssignRequest(request);
            FieldExecutive fieldExecutive = fieldExecutiveDao.getFieldExecutive(assignRequest.getFieldExecutiveId());
            orderDao.assignFieldExecutive(valueOf(assignRequest.getOrderId()), fieldExecutive);
            response.setContentType("application/json");
            response.getWriter().write(getSuccessResponse());
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("Error occurred in field executives.", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        throw new UnsupportedOperationException("No Support of GET request type for this request.");
    }

}
