package com.quifers.servlet.executive.validators;

import com.quifers.domain.id.OrderId;
import com.quifers.servlet.validations.InvalidRequestException;
import com.quifers.request.executive.ReceivableRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.servlet.http.HttpServletRequest;

import static java.lang.String.valueOf;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ReceivableRequestValidatorTest {

    private final ReceivableRequestValidator receivableRequestValidator = new ReceivableRequestValidator();

    @Test
    public void shouldPassAllValidations() throws Exception {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String orderId = "QUIFID";
        int receivables = 500;
        when(servletRequest.getParameter("order_id")).thenReturn(orderId);
        when(servletRequest.getParameter("receivables")).thenReturn(valueOf(receivables));

        //when
        ReceivableRequest receivableRequest = receivableRequestValidator.validateRequest(servletRequest);

        //then
        assertThat(receivableRequest.getOrderId(), is(new OrderId(orderId)));
        assertThat(receivableRequest.getReceivables(), is(500));
    }

    @Test
    public void shouldFailEmptyOrderIdValidations() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String orderId = "";
        int receivables = 500;
        when(servletRequest.getParameter("order_id")).thenReturn(orderId);
        when(servletRequest.getParameter("receivables")).thenReturn(valueOf(receivables));

        try {
            receivableRequestValidator.validateRequest(servletRequest);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }

    @Test
    public void shouldFailInvalidReceivablesValidations() {
        //given
        HttpServletRequest servletRequest = mock(HttpServletRequest.class);
        String orderId = "QUIFID";
        int receivables = -500;
        when(servletRequest.getParameter("order_id")).thenReturn(orderId);
        when(servletRequest.getParameter("receivables")).thenReturn(valueOf(receivables));

        try {
            receivableRequestValidator.validateRequest(servletRequest);
            Assert.fail();
        } catch (InvalidRequestException e) {
        }
    }

}
