package com.quifers.api.test;

import org.apache.activemq.broker.BrokerService;


public class ActiveMqBroker {

    private BrokerService broker;

    public ActiveMqBroker startBroker() throws Exception {
        broker = new BrokerService();
        broker.setBrokerName("WebActiveMqBroker");
        broker.setDedicatedTaskRunner(false);
        broker.setDeleteAllMessagesOnStartup(true);
        broker.addConnector("tcp://localhost:61616");
        broker.setUseShutdownHook(false);
        broker.start();
        return this;
    }

    public static void main(String[] args) throws Exception {
        new ActiveMqBroker().startBroker();
    }
}
