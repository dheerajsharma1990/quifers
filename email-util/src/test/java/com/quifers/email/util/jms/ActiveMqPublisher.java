package com.quifers.email.util.jms;

import com.quifers.Environment;
import com.quifers.dao.FieldExecutiveDao;
import com.quifers.dao.PriceDao;
import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.Price;
import com.quifers.domain.enums.OrderState;
import com.quifers.hibernate.FieldExecutiveDaoImpl;
import com.quifers.hibernate.PriceDaoImpl;
import com.quifers.hibernate.SessionFactoryBuilder;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.hibernate.SessionFactory;

import javax.jms.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class ActiveMqPublisher {

    private Session session;
    private MessageProducer producer;
    private Destination queue;

    public ActiveMqPublisher() throws JMSException {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("tcp://localhost:61616");
        Connection connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        queue = session.createQueue("QUIFERS.EMAIL.QUEUE");
        producer = session.createProducer(queue);
        producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
        connection.start();
    }

    public void publish(String type, Order order) throws JMSException {
        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setStringProperty("EMAIL_TYPE", type);
        objectMessage.setLongProperty("ORDER_ID", order.getOrderId());
        objectMessage.setObject(order);
        producer.send(objectMessage);
    }

    public static void main(String[] args) throws JMSException {
        SessionFactory sessionFactory = SessionFactoryBuilder.getSessionFactory(Environment.LOCAL);
        FieldExecutiveDao fieldExecutiveDao = new FieldExecutiveDaoImpl(sessionFactory);


        long orderId = 100l;
        Set<OrderWorkflow> workflowSet = new HashSet<>();
        workflowSet.add(new OrderWorkflow(orderId, OrderState.BOOKED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRIP_STARTED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRANSIT_STARTED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRANSIT_ENDED, new Date()));
        workflowSet.add(new OrderWorkflow(orderId, OrderState.TRIP_ENDED, new Date()));

        Order order = new Order(orderId, "name", 9988776655l, "dheerajsharma1990@gmail.com", "vehicle", "fromAddressHouseNumber",
                "fromAddressSociety", "fromAddressArea", "fromAddressCity", "toAddressHouseNumber", "toAddressSociety", "toAddressArea",
                "toAddressCity", 1, "estimate", 2, 1, false, 2, true, null, workflowSet);
        PriceDao priceDao = new PriceDaoImpl(sessionFactory);
        Price price = new Price(orderId,40,34,2,3);
        priceDao.savePrice(price);

        new ActiveMqPublisher().publish("PRICE", order);
    }


}
