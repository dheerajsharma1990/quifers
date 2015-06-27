package com.quifers.servlet.filters;

import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.servlet.CommandNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilter.class);

    protected AuthenticationRequestHandlerFactory requestHandlerFactory;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        requestHandlerFactory = new AuthenticationRequestHandlerFactory();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request;
        HttpServletResponse response = null;
        try {
            request = (HttpServletRequest) servletRequest;
            response = (HttpServletResponse) servletResponse;
            AuthenticationRequestHandler authenticationRequestHandler = requestHandlerFactory.getAuthenticationRequestHandler(request);
            authenticationRequestHandler.handleRequest(request, response, filterChain);
        } catch (CommandNotFoundException e) {
            LOGGER.error("No matching api.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            LOGGER.error("An error occurred.", e);
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroying {}", AuthenticationFilter.class);
    }
}
