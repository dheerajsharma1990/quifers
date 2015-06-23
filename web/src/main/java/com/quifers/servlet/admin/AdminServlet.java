package com.quifers.servlet.admin;

import com.quifers.domain.FieldExecutive;
import com.quifers.request.FieldExecutiveAssignRequest;
import com.quifers.request.validators.InvalidRequestException;
import com.quifers.response.FieldExecutiveResponse;
import com.quifers.servlet.BaseServlet;
import com.quifers.servlet.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

import static com.quifers.response.FieldExecutiveResponse.getSuccessResponse;

public class AdminServlet extends BaseServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminServlet.class);


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String requestUri = request.getRequestURI();
            if ("/api/v0/admin/executives/assign".equals(requestUri)) {
                FieldExecutiveAssignRequest assignRequest = new FieldExecutiveAssignRequest(request);
                FieldExecutive fieldExecutive = fieldExecutiveDao.getFieldExecutive(assignRequest.getFieldExecutiveId());
                orderDao.assignFieldExecutive(assignRequest.getOrderId(), fieldExecutive);
                response.setContentType("application/json");
                response.getWriter().write(getSuccessResponse());
            } else if ("/api/v0/admin/executives/listAll".equals(requestUri)) {
                Collection<FieldExecutive> allFieldExecutives = fieldExecutiveDao.getAllFieldExecutives();
                response.setContentType("application/json");
                response.getWriter().write(FieldExecutiveResponse.getAllFieldExecutivesResponse(allFieldExecutives));
            } else if ("/api/v0/admin/fieldExecutive".equals(requestUri)) {
                RequestHandler requestHandler = fieldExecutiveRequestHandlerFactory.getRequestHandler(request);
                requestHandler.handleRequest(request, response);
            } else if ("/api/v0/admin/order".equals(requestUri)) {
                RequestHandler requestHandler = orderRequestHandlerFactory.getRequestHandler(request);
                requestHandler.handleRequest(request, response);
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
