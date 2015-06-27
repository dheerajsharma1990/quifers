package com.quifers.service;

import org.testng.annotations.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class OrderIdGeneratorServiceTest {

    @Test
    public void shouldGenerateCorrectFormattedOrderId() {
        //given
        OrderIdGeneratorService service = new OrderIdGeneratorService(10);

        //when
        String orderId = service.getNewOrderId();

        //then
        assertThat(orderId,is("QUIFID00000011"));
    }
}
