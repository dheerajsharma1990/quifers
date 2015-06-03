package com.quifers.hibernate;

import com.quifers.Environment;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class SessionFactoryBuilder {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory(Environment environment) {
        Configuration configuration = new Configuration();
        configuration.configure("properties" + "/" + environment.name().toLowerCase() + "/hibernate-config.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        return configuration.buildSessionFactory(serviceRegistry);
    }

    public static SessionFactory getSessionFactory(Environment environment) {
        return sessionFactory == null ? buildSessionFactory(environment) : sessionFactory;
    }
}
