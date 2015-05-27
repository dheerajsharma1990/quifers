package com.quifers.email.credentials.servlet;

import com.quifers.email.credentials.builders.AccessTokenRequestBuilder;
import com.quifers.email.util.HttpRequestSender;
import org.apache.commons.io.FileUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

import static com.quifers.email.credentials.servlet.StartupContextListener.ACCESS_TOKEN_REQUEST_BUILDER;
import static com.quifers.email.credentials.servlet.StartupContextListener.HTTP_REQUEST_SENDER;

public class AccessTokenRequestServlet extends HttpServlet {

    private static final String ACCESS_TOKEN_URL = "https://accounts.google.com/o/oauth2/token";

    private HttpRequestSender httpRequestSender;
    private AccessTokenRequestBuilder requestBuilder;

    @Override
    public void init() throws ServletException {
        httpRequestSender = (HttpRequestSender) getServletContext().getAttribute(HTTP_REQUEST_SENDER);
        requestBuilder = (AccessTokenRequestBuilder) getServletContext().getAttribute(ACCESS_TOKEN_REQUEST_BUILDER);
    }

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String error = request.getParameter("error");
        if (error != null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, error);
            return;
        }

        String accessCode = request.getParameter("code");
        String requestParams = requestBuilder.buildAccessTokenRequest(accessCode, "http://localhost:8008/callback", AccessCodeRequestServlet.CLIENT_ID, AccessCodeRequestServlet.CLIENT_SECRET_KEY);
        String responseString = httpRequestSender.sendRequestAndGetResponse(ACCESS_TOKEN_URL, "POST", requestParams);
        FileUtils.writeStringToFile(new File("./target/credentials.json"), responseString);
        response.getWriter().write("Credentials successfully written to file ./target/credentials.json.\n\n" + responseString);
    }

}
