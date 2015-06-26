package com.quifers.servlet.guest;

import com.quifers.request.validators.InvalidRequestException;
import com.quifers.servlet.BaseServlet;
import com.quifers.servlet.RequestHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class GuestServlet extends BaseServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(GuestServlet.class);

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            RequestHandler requestHandler = guestRequestHandlerFactory.getRequestHandler(request);
            requestHandler.handleRequest(request, response);
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
