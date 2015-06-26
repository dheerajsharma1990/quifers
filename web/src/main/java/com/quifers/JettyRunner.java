package com.quifers;

import com.quifers.servlet.OrderServlet;
import com.quifers.servlet.admin.AdminLoginServlet;
import com.quifers.servlet.admin.AdminRegisterServlet;
import com.quifers.servlet.admin.AdminServlet;
import com.quifers.servlet.executives.FieldExecutiveLoginServlet;
import com.quifers.servlet.executives.FieldExecutiveServlet;
import com.quifers.servlet.filters.AdminAuthenticationFilter;
import com.quifers.servlet.filters.FieldExecutiveAuthenticationFilter;
import com.quifers.servlet.listener.StartupContextListener;
import org.apache.log4j.PropertyConfigurator;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

import javax.servlet.DispatcherType;
import java.io.InputStream;
import java.util.EnumSet;

public class JettyRunner {

    public static Server runJettyServer(Environment environment, int port, long lastOrderIdCounter) throws Exception {
        loadLog4jProperties(environment);
        Server server = new Server(port);

        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setInitParameter("env", environment.name());
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

    private static Environment getEnvironment() {
        String envProperty = System.getProperty("env");
        if (envProperty == null || envProperty.trim().equals("")) {
            throw new IllegalArgumentException("No environment specified.");
        }
        try {
            return Environment.valueOf(envProperty.toUpperCase().trim());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid environment specified.", e);
        }
    }

    private static void loadLog4jProperties(Environment environment) {
        InputStream inputStream = JettyRunner.class.getClassLoader().getResourceAsStream("properties/" + environment.name().toLowerCase() + "/log4j.properties");
        PropertyConfigurator.configure(inputStream);
    }

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Please provide last order number as the first argument.");
        }
        Environment environment = getEnvironment();
        Server server = runJettyServer(environment, 80, Long.valueOf(args[0]));
        server.join();
    }

}
