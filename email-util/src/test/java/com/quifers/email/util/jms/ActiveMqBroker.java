package com.quifers.email.util.jms;

import org.apache.activemq.broker.BrokerService;

import static com.quifers.email.jms.OrderListener.ACTIVEMQ_URL;

public class ActiveMqBroker {

    private BrokerService broker;

    public ActiveMqBroker startBroker() throws Exception {
        broker = new BrokerService();
        broker.setBrokerName("EmailActiveMqBroker");
        broker.setDedicatedTaskRunner(false);
        //broker.setUseJmx(false);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.addConnector(ACTIVEMQ_URL);
        broker.setUseShutdownHook(false);
        broker.start();

        return this;
    }

    public void stopBroker() throws Exception {
        broker.stop();
    }

}
