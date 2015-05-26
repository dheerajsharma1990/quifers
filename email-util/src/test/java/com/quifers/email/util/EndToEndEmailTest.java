package com.quifers.email.util;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailSender;
import com.quifers.email.jms.OrderListener;
import com.quifers.email.util.jms.ActiveMqBroker;
import com.quifers.email.util.jms.ActiveMqPublisher;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import javax.jms.JMSException;
import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

//@Test(enabled = false)
public class EndToEndEmailTest {

    private ActiveMqBroker broker = new ActiveMqBroker();
    private ActiveMqPublisher activeMqPublisher;

    @Test
    public void shouldSendEmail() throws Exception {
        //given
        Order dummyOrder = buildOrder();
        activeMqPublisher.publishOrder(dummyOrder);
        Thread.sleep(10 * 1000);
    }

    @BeforeClass
    public void startActiveMqBrokerAndEmailService() throws Exception {
        broker.startBroker();
        activeMqPublisher = new ActiveMqPublisher();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    new OrderListener(new EmailSender(new EmailCreator()), activeMqPublisher.getMessageConsumer()).listenForOrders();
                } catch (JMSException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (MessagingException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @AfterClass
    public void stopActiveMqBroker() throws Exception {
        broker.stopBroker();
    }

    private Order buildOrder() {
        Order order = new Order(2l, "Rob", 9988776655l, "dheerajsharma1990@gmail.com", "237, Phase III",
                "456, Phase IV", null, Arrays.asList(new OrderWorkflow(2l, OrderState.BOOKED, new Date())));
        return order;
    }

}
