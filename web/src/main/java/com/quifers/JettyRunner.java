package com.quifers;

import com.quifers.servlet.OrderServlet;
import com.quifers.servlet.admin.*;
import com.quifers.servlet.executives.ChangeOrderStateServlet;
import com.quifers.servlet.executives.FieldExecutiveLoginServlet;
import com.quifers.servlet.executives.GeneratePriceServlet;
import com.quifers.servlet.executives.AllOrdersOfFieldExecutiveServlet;
import com.quifers.servlet.filters.AdminAuthenticationFilter;
import com.quifers.servlet.filters.FieldExecutiveAuthenticationFilter;
import com.quifers.servlet.listener.StartupContextListener;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.util.EnumSet;

public class JettyRunner {

    public static Server runJettyServer(int port) throws Exception {

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        String envProperty = System.getProperty("env");
        String env = envProperty == null ? "LOCAL" : envProperty.toUpperCase();
        context.setInitParameter("env", env);
        context.addEventListener(new StartupContextListener());
        context.addServlet(new ServletHolder(new AdminLoginServlet()), "/api/v0/guest/admin/login");
        context.addServlet(new ServletHolder(new AdminRegisterServlet()), "/api/v0/guest/admin/register");
        context.addServlet(new ServletHolder(new FieldExecutiveAssignServlet()), "/api/v0/admin/executives/assign");
        context.addServlet(new ServletHolder(new FieldExecutiveListAllServlet()), "/api/v0/admin/executives/listAll");
        context.addServlet(new ServletHolder(new FieldExecutiveRegisterServlet()), "/api/v0/admin/executives/register");
        context.addServlet(new ServletHolder(new ChangeOrderStateServlet()), "/api/v0/executive/order/update/state");
        context.addServlet(new ServletHolder(new FieldExecutiveLoginServlet()), "/api/v0/guest/executive/login");
        context.addServlet(new ServletHolder(new OrderServlet()), "/api/v0/guest/order/*");
        context.addServlet(new ServletHolder(new GeneratePriceServlet()), "/api/v0/executive/order/create/price");
        context.addServlet(new ServletHolder(new AllOrdersOfFieldExecutiveServlet()), "/api/v0/executive/order/get/all");
        context.addFilter(new FilterHolder(new AdminAuthenticationFilter()), "/api/v0/admin/*", EnumSet.of(DispatcherType.REQUEST));
        context.addFilter(new FilterHolder(new FieldExecutiveAuthenticationFilter()), "/api/v0/executive/*", EnumSet.of(DispatcherType.REQUEST));
        server.setHandler(context);

        server.start();

        return server;
    }

    public static void main(String[] args) throws Exception {
        Server server = runJettyServer(9111);
        server.join();
    }

}
