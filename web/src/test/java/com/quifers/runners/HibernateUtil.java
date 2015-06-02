package com.quifers.runners;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.Date;

public class HibernateUtil {

    private static SessionFactory sessionFactory;

    private static SessionFactory buildSessionFactory() {
        try {

            Configuration configuration = new Configuration();
            configuration.configure("properties/local/hibernate-config.xml");
            System.out.println("Hibernate Configuration loaded");

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            System.out.println("Hibernate serviceRegistry created");

            SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);

            return sessionFactory;
        }
        catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            ex.printStackTrace();
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) sessionFactory = buildSessionFactory();
        return sessionFactory;
    }

    public static void main(String[] args) {
        SessionFactory sessionFactory1 = HibernateUtil.getSessionFactory();
        Session session = sessionFactory1.openSession();

        Order order = new Order();
        order.setOrderId(12l);
        order.setName("Name");
        order.setMobileNumber(9988l);
        OrderWorkflow workflow = new OrderWorkflow(order.getOrderId(), OrderState.BOOKED,new Date());
        order.addOrderWorkflow(workflow);
        session.save(order);

        Order o = (Order) session.get(Order.class,12l);

        session.close();
        System.out.println();
    }
}
