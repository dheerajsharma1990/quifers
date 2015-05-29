package com.quifers.servlet.filters;

import com.quifers.request.FilterRequest;
import com.quifers.request.validators.AuthenticationRequestValidator;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.servlet.listener.StartupContextListener.AUTHENTICATION_REQUEST_VALIDATOR;

public class FieldExecutiveAuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(FieldExecutiveAuthenticationFilter.class);

    private AuthenticationRequestValidator validator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        validator = (AuthenticationRequestValidator) filterConfig.getServletContext().getAttribute(AUTHENTICATION_REQUEST_VALIDATOR);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            FilterRequest filterRequest = new FilterRequest((HttpServletRequest) servletRequest);
            boolean valid = validator.validateFieldExecutve(filterRequest);
            if (!valid) {
                ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid Credentials.");
                return;
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (InvalidRequestException e) {
            LOGGER.error("Error in validation.", e);
            ((HttpServletResponse) servletResponse).sendError(HttpServletResponse.SC_BAD_REQUEST, e.getMessage());
        }
    }

    @Override
    public void destroy() {
        LOGGER.info("Destroying {}", FieldExecutiveAuthenticationFilter.class);
    }
}
