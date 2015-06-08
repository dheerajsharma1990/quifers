package com.quifers.hibernate;

import com.quifers.Environment;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DaoFactoryBuilder {

    private static DaoFactory daoFactory;

    private static DaoFactory buildDaoFactory(Environment environment) {
        Configuration configuration = new Configuration();
        configuration.configure("properties" + "/" + environment.name().toLowerCase() + "/hibernate-config.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        return new DaoFactory(sessionFactory);
    }

    public static DaoFactory getDaoFactory(Environment environment) {
        if (daoFactory == null) {
            daoFactory = buildDaoFactory(environment);
        }
        return daoFactory;
    }
}
