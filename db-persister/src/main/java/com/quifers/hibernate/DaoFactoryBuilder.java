package com.quifers.hibernate;

import com.quifers.Environment;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

public class DaoFactoryBuilder {

    private static DaoFactory daoFactory;

    private static DaoFactory buildDaoFactory(Environment environment) throws IOException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate/hibernate-config.xml");
        Properties properties = new Properties();
        String pathToProperties = "properties" + "/" + environment.name().toLowerCase() + "/hibernate.properties";
        properties.load(DaoFactoryBuilder.class.getClassLoader().getResourceAsStream(pathToProperties));
        configuration.addProperties(properties);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return new DaoFactory(sessionFactory);
    }

    public static DaoFactory getDaoFactory(Environment environment) throws IOException {
        if (daoFactory == null) {
            daoFactory = buildDaoFactory(environment);
        }
        return daoFactory;
    }
}
