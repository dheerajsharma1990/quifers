package com.quifers.email.util;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.email.EmailService;
import com.quifers.email.helpers.EmailCreator;
import com.quifers.email.helpers.EmailSender;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EndToEndEmailTest {

    private final EmailSender emailSender = new EmailSender(new EmailCreator());

    @Test(enabled = false)
    public void shouldSendEmail() throws Exception {
        //given
        Order dummyOrder = buildOrder();

        //when
        int responseCode = emailSender.sendEmail(CredentialsService.SERVICE.getCredentials(), dummyOrder, "dheeraj.sharma.aws@gmail.com");

        //then
        assertThat(responseCode, is(200));
    }

    @BeforeClass
    public void readCredentialsFromFile() throws IOException {
        EmailService.initCredentialsService(new JsonParser());
    }

    private Order buildOrder() {
        Order order = new Order(2l, "Rob", 9988776655l, "dheeraj.sharma@snapdeal.com", "237, Phase III",
                "456, Phase IV", null, Arrays.asList(new OrderWorkflow(2l, OrderState.BOOKED, new Date())));
        return order;
    }

}
