package com.quifers.servlet.filters;

import com.quifers.request.validators.AdminListAllExecutiveRequestValidator;
import com.quifers.request.validators.InvalidRequestException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.listener.StartupContextListener.ADMIN_LIST_ALL_REQUEST_VALIDATOR;

public class AdminAuthenticationFilter implements Filter {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminAuthenticationFilter.class);

    private AdminListAllExecutiveRequestValidator validator;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        validator = (AdminListAllExecutiveRequestValidator) filterConfig.getServletContext().getAttribute(ADMIN_LIST_ALL_REQUEST_VALIDATOR);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            boolean valid = validator.validateAdminListAllExecutiveRequest((HttpServletRequest) servletRequest);
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
