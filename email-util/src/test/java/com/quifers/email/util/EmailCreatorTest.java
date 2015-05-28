package com.quifers.email.util;

import com.quifers.domain.Order;
import com.quifers.domain.OrderWorkflow;
import com.quifers.domain.enums.OrderState;
import com.quifers.email.helpers.EmailCreator;
import org.hamcrest.core.Is;
import org.testng.annotations.Test;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Arrays;
import java.util.Date;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class EmailCreatorTest {

    private final EmailCreator emailCreator = new EmailCreator();

    @Test
    public void shouldCreateMimeMessageFromOrder() throws Exception {
        //given
        Date effectiveTime = new Date();
        Order order = new Order(100l, "Bob Martin", 9988776655l, "some@email.com", "123, Source", "456, Destination", null,
                Arrays.asList(new OrderWorkflow(100l, OrderState.BOOKED, effectiveTime)));

        //when
        MimeMessage message = emailCreator.createEmail(order, "sender@email.com");

        //then
        assertThat(message.getFrom()[0], Is.<Address>is(new InternetAddress("sender@email.com", "Team Quifers")));
        assertThat(message.getRecipients(Message.RecipientType.TO)[0], Is.<Address>is(new InternetAddress(order.getEmail())));
        assertThat(message.getSubject(), is("Confirmation for your Quifers Booking.Order Id [100]"));
        assertThat(message.getContentType(), is("text/html"));
        /*assertThat((String) message.getContent(), is("<html>\n" +
                "<body>\n" +
                "Hey Bob Martin,\n" +
                "<br>\n" +
                "Thanks for placing order with Quifers.Below are your order details.<br>\n" +
                "Order Id: 100<br>\n" +
                "Name: Bob Martin<br>\n" +
                "From: 123, Source<br>\n" +
                "To: 456, Destination<br>\n" +
                "Booking Time: " + effectiveTime + "<br>\n" +
                "Order State: BOOKED<br>\n" +
                "Thanks\n" +
                "<strong>Team Quifers</strong>\n" +
                "</body>\n" +
                "</html>"));*/
    }
}
