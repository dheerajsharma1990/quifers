package com.quifers.email.web.servlet;

import com.quifers.email.web.RequestParamBuilder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AccessCodeRequestServlet extends HttpServlet {

    private static final String ACCESS_CODE_URL = "https://accounts.google.com/o/oauth2/auth";

    public static final String CLIENT_ID = "218470440928-dfgf2q24h4ra6djtu82u44tongdmqcg7.apps.googleusercontent.com";
    public static final String CLIENT_SECRET_KEY = "5AkS9WeglWR5fKv27LPhUoar";

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestParamBuilder requestParamBuilder = new RequestParamBuilder();
        String requestParam = requestParamBuilder.addParam("response_type", "code")
                .addParam("client_id", CLIENT_ID)
                .addParam("redirect_uri", "http://localhost:8008/callback")
                .addParam("scope", "https://www.googleapis.com/auth/gmail.compose")
                .addParam("state", "FirstAccessCode")
                .addParam("access_type", "offline")
                .addParam("approval_prompt", "force")
                .addParam("login_hint", "dheeraj.sharma.aws@gmail.com").build();

        response.sendRedirect(ACCESS_CODE_URL + "?" + requestParam);
    }

}
