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

public class AdminAuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthenticationFilter.class);

    private AuthenticationRequestValidator validator = new AuthenticationRequestValidator();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            FilterRequest filterRequest = new FilterRequest((HttpServletRequest) servletRequest);
            boolean valid = validator.validateAdmin(filterRequest);
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
        LOGGER.info("Destroying {}", AdminAuthenticationFilter.class);
    }
}
