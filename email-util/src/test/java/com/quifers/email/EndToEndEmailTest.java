package com.quifers.email;

import com.quifers.dao.OrderDao;
import com.quifers.email.util.jms.ActiveMqBroker;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class EndToEndEmailTest {

    private OrderDao orderDao;

    @Test
    public void shouldSendEmailSuccessfully() {

    }

    @BeforeClass
    public void initialiseEmailService() throws Exception {
        new ActiveMqBroker().startBroker();

    }

}
