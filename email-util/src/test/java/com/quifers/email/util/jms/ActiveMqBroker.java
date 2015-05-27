package com.quifers.email.util.jms;

import com.quifers.email.EmailService;import org.apache.activemq.broker.BrokerService;


public class ActiveMqBroker {

    private BrokerService broker;

    public ActiveMqBroker startBroker() throws Exception {
        broker = new BrokerService();
        broker.setBrokerName("EmailActiveMqBroker");
        broker.setDedicatedTaskRunner(false);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.addConnector(EmailService.ACTIVEMQ_URL);
        broker.setUseShutdownHook(false);
        broker.start();
        return this;
    }

    public void stopBroker() throws Exception {
        broker.stop();
    }

    public static void main(String[] args) throws Exception {
        new ActiveMqBroker().startBroker();
    }
}
