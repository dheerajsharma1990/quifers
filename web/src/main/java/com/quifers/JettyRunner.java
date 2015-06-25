package com.quifers;

import com.quifers.servlet.OrderServlet;
import com.quifers.servlet.admin.*;
import com.quifers.servlet.executives.*;
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

    public static Server runJettyServer(int port, long lastOrderIdCounter) throws Exception {

        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        String envProperty = System.getProperty("env");
        String env = envProperty == null ? "LOCAL" : envProperty.toUpperCase();
        context.setInitParameter("env", env);
        context.setInitParameter("lastOrderIdCounter", String.valueOf(lastOrderIdCounter));
        context.addEventListener(new StartupContextListener());
        context.addServlet(new ServletHolder(new AdminLoginServlet()), "/api/v0/guest/admin/login");
        context.addServlet(new ServletHolder(new AdminRegisterServlet()), "/api/v0/guest/admin/register");
        context.addServlet(new ServletHolder(new FieldExecutiveLoginServlet()), "/api/v0/guest/executive/login");
        context.addServlet(new ServletHolder(new OrderServlet()), "/api/v0/guest/order/book");
        context.addServlet(new ServletHolder(new AdminServlet()), "/api/v0/admin/*");
        context.addFilter(new FilterHolder(new AdminAuthenticationFilter()), "/api/v0/admin/*", EnumSet.of(DispatcherType.REQUEST));


        context.addServlet(new ServletHolder(new FieldExecutiveServlet()), "/api/v0/executive/*");
        context.addFilter(new FilterHolder(new FieldExecutiveAuthenticationFilter()), "/api/v0/executive/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(context);

        server.start();

        return server;
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Please provide last order number as the first argument.");
        }
        Server server = runJettyServer(80, Long.valueOf(args[0]));
        server.join();
    }

}
