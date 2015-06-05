package com.quifers.request.validators;

import com.quifers.service.OrderIdGeneratorService;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.testng.Assert.fail;

public class OrderBookRequestValidatorTest {

    private OrderIdGeneratorService orderIdGeneratorService = mock(OrderIdGeneratorService.class);
    private final OrderBookRequestValidator validator = new OrderBookRequestValidator(orderIdGeneratorService);

    @Test
    public void shouldThrowInvalidRequestExceptionOnEmptyClientName() {
        //given
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getParameter("client_name")).thenReturn(null);

        //when
        try {
            validator.validateRequest(request);
            fail();
        } catch (InvalidRequestException e) {
            assertThat(e.getMessage(), is("Client Name cannot be empty."));
        }
    }
}
