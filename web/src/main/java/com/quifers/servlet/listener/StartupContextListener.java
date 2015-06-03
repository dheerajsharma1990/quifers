package com.quifers.servlet.listener;

import com.quifers.Environment;
import com.quifers.authentication.AccessTokenGenerator;
import com.quifers.authentication.AdminAuthenticator;
import com.quifers.authentication.FieldExecutiveAuthenticator;
import com.quifers.dao.AdminDao;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.hibernate.AdminDaoImpl;
import com.quifers.hibernate.FieldExecutiveDaoImpl;
import com.quifers.hibernate.OrderDaoImpl;
import com.quifers.hibernate.SessionFactoryBuilder;
import com.quifers.request.validators.AdminAccountRegisterRequestValidator;
import com.quifers.request.validators.AuthenticationRequestValidator;
import com.quifers.request.validators.OrderBookRequestValidator;
import com.quifers.request.validators.admin.AdminRegisterRequestValidator;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.util.concurrent.atomic.AtomicLong;

public class StartupContextListener implements ServletContextListener {

    public static final String ORDER_ID_COUNTER = "ORDER_ID_COUNTER";
    public static final String ORDER_BOOK_REQUEST_VALIDATOR = "ORDER_BOOK_REQUEST_VALIDATOR";
    public static final String ADMIN_ACCOUNT_REQUEST_VALIDATOR = "ADMIN_ACCOUNT_REQUEST_VALIDATOR";
    public static final String ADMIN_REQUEST_VALIDATOR = "ADMIN_REQUEST_VALIDATOR";
    public static final String ADMIN_AUTHENTICATOR = "ADMIN_AUTHENTICATOR";
    public static final String FIELD_EXECUTIVE_AUTHENTICATOR = "FIELD_EXECUTIVE_AUTHENTICATOR";
    public static final String ADMIN_TOKEN_GENERATOR = "ADMIN_TOKEN_GENERATOR";
    public static final String AUTHENTICATION_REQUEST_VALIDATOR = "AUTHENTICATION_REQUEST_VALIDATOR";

    public static final String ADMIN_DAO = "ADMIN_DAO";
    public static final String FIELD_EXECUTIVE_DAO = "FIELD_EXECUTIVE_DAO";
    public static final String ORDER_DAO = "ORDER_DAO";


    private static final Logger LOGGER = LoggerFactory.getLogger(StartupContextListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        LOGGER.info("Starting quifers webapp...");
        ServletContext servletContext = servletContextEvent.getServletContext();
        Environment environment = getEnvironment(servletContext);
        initDaos(servletContext, environment);
        AtomicLong counter = initialiseOrderId(servletContext);
        initialiseDao(servletContext);
        initialiseValidators(servletContext, counter);
    }

    private Environment getEnvironment(ServletContext servletContext) {
        String env = servletContext.getInitParameter("env");
        if (env == null) {
            throw new IllegalArgumentException("No environment specified for running application.Available environments " + Environment.values().toString());
        }
        Environment environment;
        try {
            environment = Environment.valueOf(env.toUpperCase());
        } catch (IllegalArgumentException exception) {
            throw new IllegalArgumentException("No such Environment [" + env + "] present in Environment enum.", exception);
        }
        return environment;
    }

    public AtomicLong initialiseOrderId(ServletContext servletContext) {
        AtomicLong counter = new AtomicLong(1L);
        servletContext.setAttribute(ORDER_ID_COUNTER, counter);
        return counter;
    }

    public void initialiseDao(ServletContext servletContext) {
        servletContext.setAttribute(ADMIN_ACCOUNT_REQUEST_VALIDATOR, new AdminAccountRegisterRequestValidator());
        servletContext.setAttribute(ADMIN_REQUEST_VALIDATOR, new AdminRegisterRequestValidator());
        servletContext.setAttribute(ADMIN_TOKEN_GENERATOR, new AccessTokenGenerator());
        servletContext.setAttribute(AUTHENTICATION_REQUEST_VALIDATOR, new AuthenticationRequestValidator());
    }


    private void initialiseValidators(ServletContext servletContext, AtomicLong counter) {
        servletContext.setAttribute(ORDER_BOOK_REQUEST_VALIDATOR, new OrderBookRequestValidator(counter));
    }

    private void initDaos(ServletContext servletContext, Environment environment) {
        SessionFactory sessionFactory = SessionFactoryBuilder.getSessionFactory(environment);
        AdminDao adminDao = new AdminDaoImpl(sessionFactory);
        FieldExecutiveDao fieldExecutiveDao = new FieldExecutiveDaoImpl(sessionFactory);
        servletContext.setAttribute(ADMIN_DAO, adminDao);
        servletContext.setAttribute(FIELD_EXECUTIVE_DAO, fieldExecutiveDao);
        servletContext.setAttribute(ADMIN_AUTHENTICATOR, new AdminAuthenticator(adminDao));
        servletContext.setAttribute(FIELD_EXECUTIVE_AUTHENTICATOR, new FieldExecutiveAuthenticator(fieldExecutiveDao));
        servletContext.setAttribute(ORDER_DAO, new OrderDaoImpl(sessionFactory, fieldExecutiveDao));
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        LOGGER.info("Stopping quifers webapp...");
    }
}
