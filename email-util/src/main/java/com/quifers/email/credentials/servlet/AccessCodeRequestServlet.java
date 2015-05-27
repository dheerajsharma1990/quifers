package com.quifers.email.credentials.servlet;

import com.quifers.email.credentials.builders.AccessCodeRequestBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.quifers.email.credentials.servlet.StartupContextListener.ACCESS_CODE_REQUEST_BUILDER;

public class AccessCodeRequestServlet extends HttpServlet {

    private static final String ACCESS_CODE_URL = "https://accounts.google.com/o/oauth2/auth";
    public static final String CLIENT_ID = "218470440928-dfgf2q24h4ra6djtu82u44tongdmqcg7.apps.googleusercontent.com";
    public static final String CLIENT_SECRET_KEY = "5AkS9WeglWR5fKv27LPhUoar";

    private AccessCodeRequestBuilder requestBuilder;

    @Override
    public void init() throws ServletException {
        requestBuilder = (AccessCodeRequestBuilder) getServletContext().getAttribute(ACCESS_CODE_REQUEST_BUILDER);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String requestParam = requestBuilder.buildAccessCodeRequest(CLIENT_ID, "http://localhost:8008/callback", "dheeraj.sharma.aws@gmail.com");
        response.sendRedirect(ACCESS_CODE_URL + "?" + requestParam);
    }

}
