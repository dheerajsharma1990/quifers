package com.quifers;

import com.quifers.servlet.BaseServlet;
import com.quifers.servlet.filters.AuthenticationFilter;
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

    public static void main(String[] args) throws Exception {
        if (args.length != 1) {
            throw new Exception("Please provide last order number as the first argument.");
        }
        Server server = runJettyServer(System.getProperty("env"), 80, Long.valueOf(args[0]));
        server.join();
    }

    public static Server runJettyServer(String env, int port, long lastOrderIdCounter) throws Exception {
        Environment environment = Environment.getEnvironment(env);
        loadLog4jProperties(environment);

        return runServer(env, port, lastOrderIdCounter);
    }

    private static void loadLog4jProperties(Environment environment) {
        InputStream inputStream = JettyRunner.class.getClassLoader().getResourceAsStream(environment.getPropertiesFilePath("log4j.properties"));
        PropertyConfigurator.configure(inputStream);
    }

    private static Server runServer(String env, int port, long lastOrderIdCounter) throws Exception {
        Server server = new Server(port);
        ServletContextHandler context = new ServletContextHandler(ServletContextHandler.SESSIONS);
        context.setContextPath("/");
        context.setInitParameter("env", env);
        context.setInitParameter("lastOrderIdCounter", String.valueOf(lastOrderIdCounter));
        context.addEventListener(new StartupContextListener());

        context.addServlet(new ServletHolder(new BaseServlet()), "/*");
        context.addFilter(new FilterHolder(new AuthenticationFilter()), "/*", EnumSet.of(DispatcherType.REQUEST));

        server.setHandler(context);
        server.start();

        return server;
    }

}
