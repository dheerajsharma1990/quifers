package com.quifers.db;

import com.quifers.Environment;
import com.quifers.hibernate.DaoFactoryBuilder;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.Properties;

public class Util {
    public static void main(String[] args) throws IOException {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate/hibernate-config.xml");
        Properties properties = new Properties();
        String pathToProperties = "properties" + "/" + Environment.LOCAL.name().toLowerCase() + "/hibernate.properties";
        properties.load(DaoFactoryBuilder.class.getClassLoader().getResourceAsStream(pathToProperties));
        configuration.addProperties(properties);
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();

        transaction.commit();
        session.close();
        sessionFactory.close();

    }
}
