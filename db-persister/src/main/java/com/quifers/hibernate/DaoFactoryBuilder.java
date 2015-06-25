package com.quifers.hibernate;

import com.quifers.Environment;
import com.quifers.properties.DBPersisterProperties;
import com.quifers.properties.DBPersisterPropertiesLoader;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;

public class DaoFactoryBuilder {

    private static DaoFactory daoFactory;

    private static DaoFactory buildDaoFactory(Environment environment) throws IOException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate/hibernate-config.xml");
        DBPersisterProperties dbPersisterProperties = DBPersisterPropertiesLoader.loadDbPersisterProperties(environment);
        addDatabaseProperties(configuration, dbPersisterProperties);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return new DaoFactory(sessionFactory);
    }

    private static void addDatabaseProperties(Configuration configuration, DBPersisterProperties dbPersisterProperties) {
        configuration.setProperty("hibernate.connection.driver_class", dbPersisterProperties.getDriverClass());
        configuration.setProperty("hibernate.connection.url", dbPersisterProperties.getUrl());
        configuration.setProperty("hibernate.connection.username", dbPersisterProperties.getUserName());
        configuration.setProperty("hibernate.connection.password", dbPersisterProperties.getPassword());
    }

    public static DaoFactory getDaoFactory(Environment environment) throws IOException {
        if (daoFactory == null) {
            daoFactory = buildDaoFactory(environment);
        }
        return daoFactory;
    }
}
