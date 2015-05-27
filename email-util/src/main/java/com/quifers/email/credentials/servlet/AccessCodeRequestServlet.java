package com.quifers.email.credentials.servlet;

import com.quifers.email.credentials.builders.AccessCodeRequestBuilder;
import com.quifers.email.properties.EmailUtilProperties;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.email.credentials.servlet.StartupContextListener.ACCESS_CODE_REQUEST_BUILDER;
import static com.quifers.email.credentials.servlet.StartupContextListener.EMAIL_UTIL_PROPERTIES;

public class AccessCodeRequestServlet extends HttpServlet {

    private static final String ACCESS_CODE_URL = "https://accounts.google.com/o/oauth2/auth";

    private EmailUtilProperties properties;
    private AccessCodeRequestBuilder requestBuilder;

    @Override
    public void init() throws ServletException {
        properties = (EmailUtilProperties) getServletContext().getAttribute(EMAIL_UTIL_PROPERTIES);
        requestBuilder = (AccessCodeRequestBuilder) getServletContext().getAttribute(ACCESS_CODE_REQUEST_BUILDER);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestParam = requestBuilder.buildAccessCodeRequest(properties.getClientId(), properties.getCallbackUrl(), properties.getEmailAccount());
        response.sendRedirect(ACCESS_CODE_URL + "?" + requestParam);
    }

}
