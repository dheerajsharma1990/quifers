package com.quifers.db;

import com.quifers.Environment;
import com.quifers.domain.Address;
import com.quifers.domain.Order;
import com.quifers.domain.enums.AddressType;
import com.quifers.domain.id.AddressId;
import com.quifers.domain.id.OrderId;
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

        Order order = new Order();
        OrderId orderId = new OrderId("QUIFID000001");
        order.setOrderId(orderId);
        Address address = new Address();
        AddressId addressId = new AddressId(orderId.getOrderId(), AddressType.PICKUP);
        address.setAddressId(addressId);
        Transaction transaction = session.beginTransaction();
        session.save(order);
        session.save(address);
        transaction.commit();

        Order o = (Order) session.get(Order.class, orderId);
        Address a = (Address) session.get(Address.class, addressId);
        session.close();
        sessionFactory.close();

    }
}
